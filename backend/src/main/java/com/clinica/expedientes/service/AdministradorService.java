package com.clinica.expedientes.service;

import com.clinica.expedientes.dto.request.*;
import com.clinica.expedientes.dto.response.ExpedienteResponse;
import com.clinica.expedientes.exception.ConflictoException;
import com.clinica.expedientes.exception.EstadoInvalidoException;
import com.clinica.expedientes.exception.RecursoNoEncontradoException;
import com.clinica.expedientes.model.*;
import com.clinica.expedientes.repository.*;
import com.clinica.expedientes.security.UsuarioAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final ExpedienteRepository expedienteRepository;
    private final EntrevistaRepository entrevistaRepository;
    private final InformeConsentimientoRepository informeConsentimientoRepository;
    private final UsuarioCacheRepository usuarioCacheRepository;
    private final AuditoriaService auditoriaService;

    @Transactional
    public Expediente crearExpediente(CrearExpedienteRequest req, UsuarioAutenticado admin) {
        if (expedienteRepository.existsByIdPaciente(req.getIdPaciente()))
            throw new ConflictoException("El paciente ya tiene un expediente registrado.");

        if (!usuarioCacheRepository.existsById(req.getIdPaciente()))
            throw new RecursoNoEncontradoException("El paciente con id " + req.getIdPaciente() + " no existe.");

        if (!usuarioCacheRepository.existsById(req.getIdTerapeuta()))
            throw new RecursoNoEncontradoException("El terapeuta con id " + req.getIdTerapeuta() + " no existe.");

        Expediente exp = new Expediente();
        exp.setIdPaciente(req.getIdPaciente());
        exp.setIdTerapeuta(req.getIdTerapeuta());
        exp.setEstado(Expediente.EstadoExpediente.ACTIVO);
        exp.setFechaProxCita(req.getFechaProxCita());
        return expedienteRepository.save(exp);
    }

    @Transactional
    public Expediente cambiarEstado(Long idExpediente, CambiarEstadoExpedienteRequest req, UsuarioAutenticado admin) {
        Expediente exp = expedienteRepository.findById(idExpediente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Expediente no encontrado"));

        exp.setEstado(req.getEstado());
        expedienteRepository.save(exp);

        auditoriaService.registrar(admin, RegistroAuditoria.AccionAuditoria.CAMBIAR_ESTADO_EXPEDIENTE,
                "Expediente", String.valueOf(idExpediente), RegistroAuditoria.ResultadoAuditoria.PERMITIDO);

        return exp;
    }

    @Transactional
    public EntrevistaSocioeconomica registrarEntrevista(Long idExpediente,
                                                         EntrevistaSocioeconomicaRequest req,
                                                         UsuarioAutenticado admin) {
        Expediente exp = obtenerExpedienteActivo(idExpediente);

        EntrevistaSocioeconomica entrevista = new EntrevistaSocioeconomica();
        entrevista.setExpediente(exp);
        entrevista.setFecha(LocalDate.now());
        entrevista.setTipo(Documento.TipoDocumento.ENTREVISTA_SOCIOECONOMICA);
        entrevista.setIngresoFamiliar(req.getIngresoFamiliar());
        entrevista.setGastoAlimentacion(req.getGastoAlimentacion());
        entrevista.setLugarProcedencia(req.getLugarProcedencia());
        entrevista.setVivienda(req.getVivienda());
        entrevista.setEstadoSaludFamiliar(req.getEstadoSaludFamiliar());
        entrevistaRepository.save(entrevista);

        auditoriaService.registrar(admin, RegistroAuditoria.AccionAuditoria.REGISTRAR_ENTREVISTA,
                "EntrevistaSocioeconomica", String.valueOf(entrevista.getIdDocumento()),
                RegistroAuditoria.ResultadoAuditoria.PERMITIDO);

        return entrevista;
    }

    @Transactional
    public InformeConsentimiento registrarConsentimiento(Long idExpediente,
                                                          ConsentimientoRequest req,
                                                          UsuarioAutenticado admin) {
        Expediente exp = obtenerExpedienteActivo(idExpediente);

        InformeConsentimiento informe = new InformeConsentimiento();
        informe.setExpediente(exp);
        informe.setFecha(LocalDate.now());
        informe.setTipo(Documento.TipoDocumento.INFORME_CONSENTIMIENTO);
        informe.setCuerpoDelTexto(req.getCuerpoDelTexto());
        informe.setAcuerdoConfidencial(req.getAcuerdoConfidencial());
        informeConsentimientoRepository.save(informe);

        auditoriaService.registrar(admin, RegistroAuditoria.AccionAuditoria.REGISTRAR_CONSENTIMIENTO,
                "InformeConsentimiento", String.valueOf(informe.getIdDocumento()),
                RegistroAuditoria.ResultadoAuditoria.PERMITIDO);

        return informe;
    }

    @Transactional
    public ExpedienteResponse verExpediente(Long idExpediente, UsuarioAutenticado admin) {
        Expediente exp = expedienteRepository.findById(idExpediente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Expediente no encontrado"));
        auditoriaService.registrar(admin,
                RegistroAuditoria.AccionAuditoria.CONSULTAR_EXPEDIENTE,
                "Expediente", String.valueOf(idExpediente),
                RegistroAuditoria.ResultadoAuditoria.PERMITIDO);
        return construirExpedienteResponse(exp);
    }

    public List<Map<String, Object>> listarUsuarios(UsuarioCache.TipoUsuario tipo) {
        List<UsuarioCache> usuarios = tipo != null
                ? usuarioCacheRepository.findByTipo(tipo)
                : usuarioCacheRepository.findAll();
        return usuarios.stream()
                .map(u -> Map.<String, Object>of(
                        "id", u.getIdUsuario(),
                        "nombre", u.getNombreCompleto(),
                        "tipo", u.getTipo().name()))
                .toList();
    }

    private Expediente obtenerExpedienteActivo(Long idExpediente) {
        Expediente exp = expedienteRepository.findById(idExpediente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Expediente no encontrado"));
        if (exp.getEstado() == Expediente.EstadoExpediente.ARCHIVADO)
            throw new EstadoInvalidoException("No se puede modificar un expediente archivado.");
        return exp;
    }

    private ExpedienteResponse construirExpedienteResponse(Expediente exp) {
        String nombrePaciente = usuarioCacheRepository.findById(exp.getIdPaciente())
                .map(UsuarioCache::getNombreCompleto).orElse("Desconocido");

        List<Documento> docs = exp.getDocumentos() == null ? List.of() : exp.getDocumentos();

        List<ExpedienteResponse.ReporteResumen> reportes = docs.stream()
                .filter(d -> d.getTipo() == Documento.TipoDocumento.REPORTE_SESION)
                .map(d -> (ReporteSesion) d)
                .map(r -> ExpedienteResponse.ReporteResumen.builder()
                        .idDocumento(r.getIdDocumento())
                        .fechaSesion(r.getFechaSesion())
                        .estado(r.getEstado())
                        .build())
                .toList();

        ExpedienteResponse.DocumentoResumen entrevista = docs.stream()
                .filter(d -> d.getTipo() == Documento.TipoDocumento.ENTREVISTA_SOCIOECONOMICA)
                .findFirst()
                .map(d -> ExpedienteResponse.DocumentoResumen.builder()
                        .idDocumento(d.getIdDocumento()).fecha(d.getFecha()).build())
                .orElse(null);

        ExpedienteResponse.DocumentoResumen consentimiento = docs.stream()
                .filter(d -> d.getTipo() == Documento.TipoDocumento.INFORME_CONSENTIMIENTO)
                .findFirst()
                .map(d -> ExpedienteResponse.DocumentoResumen.builder()
                        .idDocumento(d.getIdDocumento()).fecha(d.getFecha()).build())
                .orElse(null);

        return ExpedienteResponse.builder()
                .idExpediente(exp.getIdExpediente())
                .estado(exp.getEstado())
                .fechaProxCita(exp.getFechaProxCita())
                .paciente(ExpedienteResponse.PacienteInfo.builder()
                        .idPaciente(exp.getIdPaciente())
                        .nombreCompleto(nombrePaciente)
                        .build())
                .entrevistaSocioeconomica(entrevista)
                .informeConsentimiento(consentimiento)
                .reportesSesion(reportes)
                .build();
    }
}
