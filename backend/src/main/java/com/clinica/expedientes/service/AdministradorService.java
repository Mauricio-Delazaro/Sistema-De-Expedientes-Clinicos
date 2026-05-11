package com.clinica.expedientes.service;

import com.clinica.expedientes.dto.request.*;
import com.clinica.expedientes.dto.response.*;
import com.clinica.expedientes.exception.AccesoDenegadoException;
import com.clinica.expedientes.exception.ConflictoException;
import com.clinica.expedientes.exception.RecursoNoEncontradoException;
import com.clinica.expedientes.model.*;
import com.clinica.expedientes.model.enums.*;
import com.clinica.expedientes.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AdministradorService {

    private final UsuarioRepository usuarioRepo;
    private final PacienteRepository pacienteRepo;
    private final TerapeutaRepository terapeutaRepo;
    private final ExpedienteRepository expedienteRepo;
    private final EntrevistaSocioeconomicaRepository entrevistaRepo;
    private final InformeConsentimientoRepository consentimientoRepo;
    private final AuditoriaService auditoriaService;

    public AdministradorService(UsuarioRepository usuarioRepo, PacienteRepository pacienteRepo,
                                 TerapeutaRepository terapeutaRepo, ExpedienteRepository expedienteRepo,
                                 EntrevistaSocioeconomicaRepository entrevistaRepo,
                                 InformeConsentimientoRepository consentimientoRepo,
                                 AuditoriaService auditoriaService) {
        this.usuarioRepo = usuarioRepo;
        this.pacienteRepo = pacienteRepo;
        this.terapeutaRepo = terapeutaRepo;
        this.expedienteRepo = expedienteRepo;
        this.entrevistaRepo = entrevistaRepo;
        this.consentimientoRepo = consentimientoRepo;
        this.auditoriaService = auditoriaService;
    }

    private void resolverAdministrador(Long userId) {
        Usuario u = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + userId));
        if (!(u instanceof Administrador)) {
            throw new AccesoDenegadoException("El usuario no tiene rol ADMINISTRADOR.");
        }
    }

    @Transactional
    public ExpedienteCreadoResponse crearExpediente(Long userId, CrearExpedienteRequest req) {
        resolverAdministrador(userId);

        Paciente paciente = pacienteRepo.findById(req.getIdPaciente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Paciente no encontrado: " + req.getIdPaciente()));

        Terapeuta terapeuta = terapeutaRepo.findById(req.getIdTerapeuta())
                .orElseThrow(() -> new RecursoNoEncontradoException("Terapeuta no encontrado: " + req.getIdTerapeuta()));

        if (expedienteRepo.existsByPaciente(paciente)) {
            throw new ConflictoException("El paciente ya tiene un expediente clínico registrado.");
        }

        Expediente exp = new Expediente();
        exp.setPaciente(paciente);
        exp.setTerapeuta(terapeuta);
        exp.setEstado(EstadoExpediente.ACTIVO);
        exp.setFechaProxCita(req.getFechaProxCita());

        Expediente saved = expedienteRepo.save(exp);

        return new ExpedienteCreadoResponse(
                saved.getIdExpediente(),
                paciente.getIdUsuario(),
                terapeuta.getIdUsuario(),
                saved.getEstado().name(),
                saved.getFechaProxCita()
        );
    }

    @Transactional
    public ExpedienteEstadoResponse cambiarEstado(Long userId, Long idExpediente,
                                                   CambiarEstadoExpedienteRequest req) {
        resolverAdministrador(userId);

        Expediente exp = expedienteRepo.findById(idExpediente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Expediente no encontrado: " + idExpediente));

        EstadoExpediente nuevoEstado;
        try {
            nuevoEstado = EstadoExpediente.valueOf(req.getEstado().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido: " + req.getEstado() +
                    ". Valores válidos: ACTIVO, ARCHIVADO");
        }

        exp.setEstado(nuevoEstado);
        expedienteRepo.save(exp);

        auditoriaService.registrar(userId, RolUsuario.ADMINISTRADOR, AccionAuditoria.CAMBIAR_ESTADO_EXPEDIENTE,
                "Expediente", String.valueOf(idExpediente), ResultadoAuditoria.PERMITIDO);

        return new ExpedienteEstadoResponse(exp.getIdExpediente(), exp.getEstado().name());
    }

    @Transactional
    public EntrevistaResponse registrarEntrevista(Long userId, Long idExpediente,
                                                   RegistrarEntrevistaRequest req) {
        resolverAdministrador(userId);

        Expediente exp = expedienteRepo.findById(idExpediente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Expediente no encontrado: " + idExpediente));

        EntrevistaSocioeconomica entrevista = new EntrevistaSocioeconomica();
        entrevista.setExpediente(exp);
        entrevista.setFecha(LocalDate.now());
        entrevista.setIngresoFamiliar(req.getIngresoFamiliar());
        entrevista.setGastoAlimentacion(req.getGastoAlimentacion());
        entrevista.setLugarProcedencia(req.getLugarProcedencia());
        entrevista.setVivienda(req.getVivienda());
        entrevista.setEstadoSaludFamiliar(req.getEstadoSaludFamiliar());

        EntrevistaSocioeconomica saved = entrevistaRepo.save(entrevista);

        auditoriaService.registrar(userId, RolUsuario.ADMINISTRADOR, AccionAuditoria.REGISTRAR_ENTREVISTA,
                "EntrevistaSocioeconomica", String.valueOf(saved.getIdDocumento()), ResultadoAuditoria.PERMITIDO);

        return new EntrevistaResponse(
                saved.getIdDocumento(), idExpediente, saved.getFecha(),
                saved.getIngresoFamiliar(), saved.getGastoAlimentacion(),
                saved.getLugarProcedencia(), saved.getVivienda(), saved.getEstadoSaludFamiliar()
        );
    }

    @Transactional
    public ConsentimientoResponse registrarConsentimiento(Long userId, Long idExpediente,
                                                           RegistrarConsentimientoRequest req) {
        resolverAdministrador(userId);

        Expediente exp = expedienteRepo.findById(idExpediente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Expediente no encontrado: " + idExpediente));

        InformeConsentimiento consentimiento = new InformeConsentimiento();
        consentimiento.setExpediente(exp);
        consentimiento.setFecha(LocalDate.now());
        consentimiento.setCuerpoDelTexto(req.getCuerpoDelTexto());
        consentimiento.setAcuerdoConfidencial(req.getAcuerdoConfidencial());

        InformeConsentimiento saved = consentimientoRepo.save(consentimiento);

        auditoriaService.registrar(userId, RolUsuario.ADMINISTRADOR, AccionAuditoria.REGISTRAR_CONSENTIMIENTO,
                "InformeConsentimiento", String.valueOf(saved.getIdDocumento()), ResultadoAuditoria.PERMITIDO);

        return new ConsentimientoResponse(
                saved.getIdDocumento(), idExpediente, saved.getFecha(),
                saved.getCuerpoDelTexto(), saved.getAcuerdoConfidencial()
        );
    }
}
