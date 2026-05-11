document.getElementById('userId').addEventListener('change', saveUserId);

/* ===== TABS ===== */
window.addEventListener('DOMContentLoaded', () => initTabs('#adminTabs'));

function setStatusGestion(msg, type) {
  const el = document.getElementById('statusGestion');
  if (!el) return;
  el.textContent = msg || '';
  el.className = 'status-banner' + (type ? ' sb-' + type : '');
}

function getExpedienteId() {
  const v = document.getElementById('gExpedienteId').value.trim();
  if (!v) { toast('Ingresa el ID del expediente primero', 'error'); return null; }
  return parseInt(v);
}

/* ===== TAB 1: NUEVO EXPEDIENTE ===== */
document.getElementById('btnCrearExpediente').addEventListener('click', async () => {
  const btn = document.getElementById('btnCrearExpediente');
  const idPaciente  = document.getElementById('nePaciente').value.trim();
  const idTerapeuta = document.getElementById('neTerapeuta').value.trim();
  const fechaRaw    = document.getElementById('neFecha').value;

  if (!idPaciente || !idTerapeuta) {
    toast('El ID del paciente y del terapeuta son obligatorios', 'error');
    return;
  }

  const body = {
    idPaciente:   parseInt(idPaciente),
    idTerapeuta:  parseInt(idTerapeuta),
    fechaProxCita: fechaRaw ? new Date(fechaRaw).toISOString() : null,
  };

  setLoading(btn, true);
  const result = document.getElementById('resultNuevo');
  result.innerHTML = '';

  try {
    const data = await api.post('/expedientes', body);
    toast('Expediente creado exitosamente', 'success');
    result.innerHTML = `
      <div class="alert alert-success">
        <strong>Expediente creado correctamente</strong><br/>
        ID: <strong>#${esc(data.idExpediente)}</strong> &nbsp;|&nbsp;
        Paciente: ${esc(data.idPaciente)} &nbsp;|&nbsp;
        Terapeuta: ${esc(data.idTerapeuta)} &nbsp;|&nbsp;
        Estado: ${badge(data.estado)}
        ${data.fechaProxCita ? `<br/>Prox. cita: ${fDateTime(data.fechaProxCita)}` : ''}
      </div>`;
    document.getElementById('nePaciente').value = '';
    document.getElementById('neTerapeuta').value = '';
    document.getElementById('neFecha').value = '';
  } catch (e) {
    toast(e.message, 'error');
    result.innerHTML = `<div class="alert alert-error">${esc(e.message)}</div>`;
  } finally {
    setLoading(btn, false);
  }
});

/* ===== TAB 2: CAMBIAR ESTADO ===== */
document.getElementById('btnCambiarEstado').addEventListener('click', async () => {
  const btn = document.getElementById('btnCambiarEstado');
  const id = getExpedienteId();
  if (!id) return;

  const estado = document.getElementById('gNuevoEstado').value;
  setLoading(btn, true);
  setStatusGestion('Aplicando cambio...', 'loading');
  const result = document.getElementById('resultEstado');
  result.innerHTML = '';

  try {
    const data = await api.patch(`/expedientes/${id}/estado`, { estado });
    setStatusGestion('');
    toast(`Estado cambiado a ${data.estado}`, 'success');
    result.innerHTML = `<div class="alert alert-success">Expediente #${esc(data.idExpediente)} actualizado — estado: ${badge(data.estado)}</div>`;
  } catch (e) {
    setStatusGestion('');
    toast(e.message, 'error');
    result.innerHTML = `<div class="alert alert-error">${esc(e.message)}</div>`;
  } finally {
    setLoading(btn, false);
  }
});

/* ===== TAB 2: REGISTRAR ENTREVISTA ===== */
document.getElementById('btnRegistrarEntrevista').addEventListener('click', async () => {
  const btn = document.getElementById('btnRegistrarEntrevista');
  const id = getExpedienteId();
  if (!id) return;

  const ingreso  = document.getElementById('eIngreso').value.trim();
  const gasto    = document.getElementById('eGasto').value.trim();
  const lugar    = document.getElementById('eLugar').value.trim();
  const vivienda = document.getElementById('eVivienda').value.trim();
  const salud    = document.getElementById('eSalud').value.trim();

  if (!ingreso || !gasto || !lugar || !salud) {
    toast('Completa los campos obligatorios de la entrevista', 'error');
    return;
  }

  const body = {
    ingresoFamiliar:     parseFloat(ingreso),
    gastoAlimentacion:   parseFloat(gasto),
    lugarProcedencia:    lugar,
    vivienda:            vivienda || null,
    estadoSaludFamiliar: salud,
  };

  setLoading(btn, true);
  setStatusGestion('Guardando entrevista...', 'loading');
  const result = document.getElementById('resultEntrevista');
  result.innerHTML = '';

  try {
    const data = await api.post(`/expedientes/${id}/entrevista-socioeconomica`, body);
    setStatusGestion('');
    toast('Entrevista socioeconomica registrada', 'success');
    result.innerHTML = `<div class="alert alert-success">Entrevista registrada — ID documento: #${esc(data.idDocumento)} · Fecha: ${fDate(data.fecha)}</div>`;
    document.getElementById('formEntrevista').reset();
  } catch (e) {
    setStatusGestion('');
    toast(e.message, 'error');
    result.innerHTML = `<div class="alert alert-error">${esc(e.message)}</div>`;
  } finally {
    setLoading(btn, false);
  }
});

/* ===== TAB 2: REGISTRAR CONSENTIMIENTO ===== */
document.getElementById('btnRegistrarConsentimiento').addEventListener('click', async () => {
  const btn = document.getElementById('btnRegistrarConsentimiento');
  const id = getExpedienteId();
  if (!id) return;

  const cuerpo  = document.getElementById('cCuerpo').value.trim();
  const acuerdo = document.getElementById('cAcuerdo').value.trim();

  if (!cuerpo || !acuerdo) {
    toast('El cuerpo del texto y el acuerdo son obligatorios', 'error');
    return;
  }

  setLoading(btn, true);
  setStatusGestion('Guardando consentimiento...', 'loading');
  const result = document.getElementById('resultConsentimiento');
  result.innerHTML = '';

  try {
    const data = await api.post(`/expedientes/${id}/consentimiento`, {
      cuerpoDelTexto:      cuerpo,
      acuerdoConfidencial: acuerdo,
    });
    setStatusGestion('');
    toast('Consentimiento informado registrado', 'success');
    result.innerHTML = `<div class="alert alert-success">Consentimiento registrado — ID documento: #${esc(data.idDocumento)} · Fecha: ${fDate(data.fecha)}</div>`;
    document.getElementById('formConsentimiento').reset();
  } catch (e) {
    setStatusGestion('');
    toast(e.message, 'error');
    result.innerHTML = `<div class="alert alert-error">${esc(e.message)}</div>`;
  } finally {
    setLoading(btn, false);
  }
});

/* ===== TAB 3: AUDITORIA ===== */
document.getElementById('btnBuscarAuditoria').addEventListener('click', buscarAuditoria);

async function buscarAuditoria() {
  const btn = document.getElementById('btnBuscarAuditoria');

  const idUsuario  = document.getElementById('aIdUsuario').value.trim();
  const fechaDesde = document.getElementById('aFechaDesde').value;
  const fechaHasta = document.getElementById('aFechaHasta').value;
  const accion     = document.getElementById('aAccion').value;
  const recurso    = document.getElementById('aRecurso').value.trim();
  const resultado  = document.getElementById('aResultado').value;

  let qs = '?';
  if (idUsuario)  qs += `idUsuario=${idUsuario}&`;
  if (fechaDesde) qs += `fechaDesde=${encodeURIComponent(new Date(fechaDesde).toISOString())}&`;
  if (fechaHasta) qs += `fechaHasta=${encodeURIComponent(new Date(fechaHasta).toISOString())}&`;
  if (accion)     qs += `accion=${encodeURIComponent(accion)}&`;
  if (recurso)    qs += `recurso=${encodeURIComponent(recurso)}&`;
  if (resultado)  qs += `resultado=${encodeURIComponent(resultado)}&`;

  setLoading(btn, true);
  const countEl = document.getElementById('countAuditoria');
  if (countEl) countEl.textContent = '';
  try {
    const data = await api.get(`/auditoria${qs}`);
    renderAuditoria(data);
    if (countEl) countEl.textContent = `${data.length} resultado(s)`;
    toast(`${data.length} registro(s) encontrados`, 'success');
  } catch (e) {
    toast(e.message, 'error');
    document.getElementById('auditoriaResultado').innerHTML =
      `<div class="alert alert-error" style="margin:1rem;">${esc(e.message)}</div>`;
  } finally {
    setLoading(btn, false);
  }
}

function renderAuditoria(lista) {
  const el = document.getElementById('auditoriaResultado');

  if (lista.length === 0) {
    el.innerHTML = `<div class="empty-state" style="padding:2rem;"><p>No se encontraron registros con esos filtros</p></div>`;
    return;
  }

  const rows = lista.map(l => `
    <tr>
      <td>${esc(l.idLog)}</td>
      <td>${esc(l.idUsuario)}</td>
      <td><code style="font-size:0.75rem;">${esc(l.rolUsuario)}</code></td>
      <td style="font-size:0.8125rem;white-space:nowrap;">${esc(l.accion)}</td>
      <td>${esc(l.recurso)}${l.idRecurso ? ` <span style="color:#94A3B8;">#${esc(l.idRecurso)}</span>` : ''}</td>
      <td style="white-space:nowrap;font-size:0.8125rem;">${fDateTime(l.fechaHora)}</td>
      <td>${badge(l.resultado)}</td>
    </tr>
  `).join('');

  el.innerHTML = `
    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th>ID Log</th>
            <th>Usuario</th>
            <th>Rol</th>
            <th>Accion</th>
            <th>Recurso</th>
            <th>Fecha / Hora</th>
            <th>Resultado</th>
          </tr>
        </thead>
        <tbody>${rows}</tbody>
      </table>
    </div>`;
}
