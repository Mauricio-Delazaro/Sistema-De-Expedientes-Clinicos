let expedienteActual = null;
let reporteActual = null;
let modoReporte = 'crear'; // 'crear' | 'modificar'
let todosPacientes = [];
let nombresAutocompletePacientes = [];

function setStatus(msg, type) {
  const el = document.getElementById('statusLeft');
  if (!el) return;
  el.textContent = msg || '';
  el.className = 'status-banner' + (type ? ' sb-' + type : '');
}

function setModalStatus(msg, type) {
  const el = document.getElementById('mslReporte');
  if (!el) return;
  el.textContent = msg || '';
  el.className = 'modal-status-line' + (type ? ' msl-' + type : '');
}

/* ===== PACIENTES ===== */
document.getElementById('btnLoadPacientes').addEventListener('click', loadPacientes);
document.getElementById('userId').addEventListener('change', saveUserId);

async function loadPacientes() {
  const btn = document.getElementById('btnLoadPacientes');
  setLoading(btn, true);
  setStatus('Cargando pacientes...', 'loading');
  try {
    const data = await api.get('/terapeutas/mis-pacientes');
    todosPacientes = data;
    nombresAutocompletePacientes = data.map(p => p.nombreCompleto).filter(Boolean).sort();

    const inputBuscar = document.getElementById('buscarPaciente');
    if (inputBuscar) inputBuscar.value = '';
    const searchBar = document.getElementById('pacientesSearch');
    if (searchBar) searchBar.style.display = data.length > 0 ? '' : 'none';

    renderPacientes(data);
    setStatus(`${data.length} paciente(s) cargados`, 'success');
  } catch (e) {
    setStatus(e.message, 'error');
  } finally {
    setLoading(btn, false);
  }
}

function renderPacientes(lista) {
  const el = document.getElementById('pacientesList');
  const total = todosPacientes.length;

  const grid = document.getElementById('statsGrid');
  if (grid) {
    grid.style.display = '';
    document.getElementById('statTotal').textContent = total;
    document.getElementById('statVisibles').textContent = lista.length;
  }

  if (lista.length === 0) {
    el.innerHTML = `<div class="empty-state" style="padding:1.5rem;"><p>No hay pacientes que coincidan</p></div>`;
    return;
  }

  el.innerHTML = lista.map(p => `
    <div class="patient-item" data-exp="${esc(p.idExpediente)}" onclick="selectPaciente(${p.idExpediente}, this)">
      <div class="patient-avatar">${esc(p.nombreCompleto.charAt(0))}</div>
      <div class="patient-info">
        <div class="patient-name">${esc(p.nombreCompleto)}</div>
        <div class="patient-meta">Expediente #${esc(p.idExpediente)}</div>
      </div>
    </div>
  `).join('');
}

/* ===== AUTOCOMPLETE DE PACIENTES ===== */
window.addEventListener('DOMContentLoaded', function () {
  const inputBuscar = document.getElementById('buscarPaciente');
  const listAC      = document.getElementById('acPacientes');
  if (!inputBuscar || !listAC) return;

  initAutocomplete(
    inputBuscar,
    listAC,
    function () { return nombresAutocompletePacientes; },
    function (query) {
      const q = query.trim().toLowerCase();
      const filtrados = q
        ? todosPacientes.filter(p => p.nombreCompleto.toLowerCase().includes(q))
        : todosPacientes;
      renderPacientes(filtrados);
    }
  );
});

/* ===== EXPEDIENTE ===== */
async function selectPaciente(idExpediente, el) {
  document.querySelectorAll('.patient-item').forEach(i => i.classList.remove('active'));
  el.classList.add('active');
  showLoadingPanel('Cargando expediente...');

  try {
    const data = await api.get(`/expedientes/${idExpediente}`);
    expedienteActual = data;
    reporteActual = null;
    renderExpediente(data);
  } catch (e) {
    toast(e.message, 'error');
    showErrorPanel(e.message);
  }
}

function renderExpediente(exp) {
  const p = exp.paciente;
  const panel = document.getElementById('contentPanel');

  panel.innerHTML = `
    <div class="card" style="margin-bottom:1.25rem;">
      <div class="card-header">
        <h2>Expediente #${esc(exp.idExpediente)}</h2>
        <div>${badge(exp.estado)}</div>
      </div>
      <div class="card-body">
        <div class="section-title">Datos del Paciente</div>
        <div class="info-grid" style="margin-bottom:1.25rem;">
          <div class="info-item"><div class="label">Nombre</div><div class="value">${esc(p.nombreCompleto)}</div></div>
          <div class="info-item"><div class="label">Edad</div><div class="value">${esc(p.edad)} anos</div></div>
          <div class="info-item"><div class="label">Fecha nacimiento</div><div class="value">${fDate(p.fechaNacimiento)}</div></div>
          <div class="info-item"><div class="label">Telefono</div><div class="value">${esc(p.numeroTelefonico)}</div></div>
          <div class="info-item"><div class="label">Correo</div><div class="value">${esc(p.correoElectronico) || '—'}</div></div>
          <div class="info-item"><div class="label">Prox. cita</div><div class="value">${fDateTime(exp.fechaProxCita)}</div></div>
        </div>

        <div class="section-title">Documentos</div>
        <div style="margin-bottom:1.25rem;">
          ${docItem('Entrevista Socioeconomica', exp.entrevistaSocioeconomica)}
          ${docItem('Consentimiento Informado', exp.informeConsentimiento)}
        </div>

        <div class="section-title">Reportes de Sesion</div>
        ${renderReportesList(exp.reportesSesion, exp.idExpediente)}
      </div>
    </div>
  `;
}

function docItem(nombre, doc) {
  if (doc) {
    return `
      <div class="doc-item">
        <div class="doc-icon">OK</div>
        <div class="doc-info">
          <div class="doc-name">${esc(nombre)}</div>
          <div class="doc-sub">Registrado el ${fDate(doc.fecha)} · ID ${esc(doc.idDocumento)}</div>
        </div>
      </div>`;
  }
  return `
    <div class="doc-item">
      <div class="doc-icon missing">--</div>
      <div class="doc-info">
        <div class="doc-name" style="color:#94A3B8;">${esc(nombre)}</div>
        <div class="doc-sub">No registrado</div>
      </div>
    </div>`;
}

function renderReportesList(reportes, idExpediente) {
  const crearBtn = `
    <div style="margin-bottom:0.875rem;">
      <button class="btn btn-primary btn-sm" onclick="openModalCrear(${idExpediente})">+ Nuevo Reporte</button>
    </div>`;

  if (!reportes || reportes.length === 0) {
    return crearBtn + `<div class="empty-state" style="padding:1.5rem;"><p>No hay reportes registrados</p></div>`;
  }

  const rows = reportes.map(r => `
    <tr class="clickable" onclick="loadReporte(${r.idDocumento})">
      <td>#${esc(r.idDocumento)}</td>
      <td>${fDate(r.fechaSesion)}</td>
      <td>${badge(r.estado)}</td>
      <td><button class="btn btn-secondary btn-sm" onclick="event.stopPropagation();loadReporte(${r.idDocumento})">Ver</button></td>
    </tr>
  `).join('');

  return crearBtn + `
    <div class="table-wrap">
      <table>
        <thead>
          <tr><th>ID</th><th>Fecha sesion</th><th>Estado</th><th></th></tr>
        </thead>
        <tbody>${rows}</tbody>
      </table>
    </div>`;
}

/* ===== REPORTE DETALLE ===== */
async function loadReporte(idReporte) {
  showLoadingPanel('Cargando reporte...');
  try {
    const data = await api.get(`/reportes/${idReporte}`);
    reporteActual = data;
    renderReporte(data);
  } catch (e) {
    toast(e.message, 'error');
    if (expedienteActual) renderExpediente(expedienteActual);
  }
}

function renderReporte(r) {
  const panel = document.getElementById('contentPanel');
  const acciones = buildReporteAcciones(r);

  panel.innerHTML = `
    <div class="card">
      <div class="card-header">
        <h2>Reporte #${esc(r.idDocumento)}</h2>
        <div style="display:flex;gap:0.5rem;align-items:center;">
          ${badge(r.estado)}
          <button class="btn btn-secondary btn-sm" onclick="renderExpediente(expedienteActual)">Volver</button>
        </div>
      </div>
      <div class="card-body">
        <div class="info-grid" style="margin-bottom:1.25rem;">
          <div class="info-item"><div class="label">Expediente</div><div class="value">#${esc(r.idExpediente)}</div></div>
          <div class="info-item"><div class="label">Fecha de sesion</div><div class="value">${fDate(r.fechaSesion)}</div></div>
          <div class="info-item"><div class="label">Duracion</div><div class="value">${r.duracionSesion ? esc(r.duracionSesion) + ' min' : '—'}</div></div>
          <div class="info-item"><div class="label">Creado</div><div class="value">${fDateTime(r.fechaCreacion)}</div></div>
          <div class="info-item"><div class="label">Modificado</div><div class="value">${fDateTime(r.fechaModificacion)}</div></div>
        </div>

        <div class="report-section">
          <div class="section-title">Observaciones clinicas</div>
          <div class="report-text-block">${esc(r.observacionesClinicas)}</div>
        </div>

        ${r.comentariosTerapeuta ? `
        <div class="report-section">
          <div class="section-title">Comentarios del terapeuta</div>
          <div class="report-text-block">${esc(r.comentariosTerapeuta)}</div>
        </div>` : ''}

        ${r.comentariosSupervisor ? `
        <div class="report-section">
          <div class="section-title">Retroalimentacion del supervisor</div>
          <div class="report-text-block" style="border-color:#FECACA;background:#FEF2F2;">${esc(r.comentariosSupervisor)}</div>
        </div>` : ''}

        ${acciones}
      </div>
    </div>
  `;
}

function buildReporteAcciones(r) {
  if (r.estado === 'CREADO') {
    return `<div class="action-bar">
      <button class="btn btn-primary" id="btnEnviar" onclick="enviarReporte(${r.idDocumento})">Enviar a revision</button>
    </div>`;
  }
  if (r.estado === 'RECHAZADO') {
    return `<div class="action-bar">
      <button class="btn btn-warning" onclick="openModalModificar(${r.idDocumento})">Modificar reporte</button>
      <button class="btn btn-primary" id="btnEnviar" onclick="enviarReporte(${r.idDocumento})">Enviar a revision</button>
    </div>`;
  }
  if (r.estado === 'PENDIENTE') {
    return `<div class="action-bar"><div class="alert alert-info" style="margin:0;">Este reporte esta pendiente de revision por el supervisor.</div></div>`;
  }
  if (r.estado === 'APROBADO') {
    return `<div class="action-bar"><div class="alert alert-success" style="margin:0;">Este reporte fue aprobado por el supervisor.</div></div>`;
  }
  return '';
}

/* ===== ENVIAR REPORTE ===== */
async function enviarReporte(idReporte) {
  const btn = document.getElementById('btnEnviar');
  setLoading(btn, true);
  try {
    const data = await api.patch(`/reportes/${idReporte}/enviar`);
    toast(`Reporte enviado a revision. Estado: ${data.estado}`, 'success');
    await loadReporte(idReporte);
  } catch (e) {
    toast(e.message, 'error');
    setLoading(btn, false);
  }
}

/* ===== MODAL CREAR REPORTE ===== */
let idExpedienteReporte = null;

function openModalCrear(idExpediente) {
  modoReporte = 'crear';
  idExpedienteReporte = idExpediente;
  document.getElementById('modalReporteTitulo').textContent = 'Nuevo Reporte de Sesion';
  document.getElementById('formReporte').reset();
  document.getElementById('rfFechaSesion').valueAsDate = new Date();
  openModal('modalReporte');
}

function openModalModificar(idReporte) {
  if (!reporteActual || reporteActual.idDocumento !== idReporte) return;
  modoReporte = 'modificar';
  document.getElementById('modalReporteTitulo').textContent = 'Modificar Reporte';
  const r = reporteActual;
  document.getElementById('rfFechaSesion').value = r.fechaSesion;
  document.getElementById('rfDuracion').value = r.duracionSesion || '';
  document.getElementById('rfObservaciones').value = r.observacionesClinicas || '';
  document.getElementById('rfComentarios').value = r.comentariosTerapeuta || '';
  openModal('modalReporte');
}

document.getElementById('btnGuardarReporte').addEventListener('click', async () => {
  const btn = document.getElementById('btnGuardarReporte');
  const fechaSesion   = document.getElementById('rfFechaSesion').value;
  const duracion      = document.getElementById('rfDuracion').value;
  const observaciones = document.getElementById('rfObservaciones').value.trim();
  const comentarios   = document.getElementById('rfComentarios').value.trim();

  if (!fechaSesion)   { setModalStatus('La fecha de sesión es obligatoria', 'error'); return; }
  if (!observaciones) { setModalStatus('Las observaciones clínicas son obligatorias', 'error'); return; }

  const body = {
    fechaSesion,
    duracionSesion:        duracion ? parseInt(duracion) : null,
    observacionesClinicas: observaciones,
    comentariosTerapeuta:  comentarios || null,
  };

  setModalStatus('Guardando...', 'loading');
  setLoading(btn, true);
  try {
    if (modoReporte === 'crear') {
      await api.post(`/expedientes/${idExpedienteReporte}/reportes`, body);
      closeModal('modalReporte');
      setModalStatus('');
      toast('Reporte creado exitosamente', 'success');
      const exp = await api.get(`/expedientes/${idExpedienteReporte}`);
      expedienteActual = exp;
      renderExpediente(exp);
    } else {
      await api.put(`/reportes/${reporteActual.idDocumento}`, body);
      closeModal('modalReporte');
      setModalStatus('');
      toast('Reporte modificado exitosamente', 'success');
      await loadReporte(reporteActual.idDocumento);
    }
  } catch (e) {
    setModalStatus(e.message, 'error');
  } finally {
    setLoading(btn, false);
  }
});

/* ===== HELPERS ===== */
function showLoadingPanel(msg) {
  document.getElementById('contentPanel').innerHTML = `
    <div class="card"><div class="card-body"><div class="empty-state">
      <span class="spinner"></span>
      <p style="margin-top:1rem;">${esc(msg)}</p>
    </div></div></div>`;
}

function showErrorPanel(msg) {
  document.getElementById('contentPanel').innerHTML = `
    <div class="card"><div class="card-body">
      <div class="alert alert-error">${esc(msg)}</div>
    </div></div>`;
}
