let reporteActual = null;
let reporteIdParaRechazar = null;
let todosLosReportes = [];
let nombresAutocompleteTerapeutas = [];

function setStatus(msg, type) {
  const el = document.getElementById('statusLeft');
  if (!el) return;
  el.textContent = msg || '';
  el.className = 'status-banner' + (type ? ' sb-' + type : '');
}

function setModalStatus(msg, type) {
  const el = document.getElementById('mslRechazar');
  if (!el) return;
  el.textContent = msg || '';
  el.className = 'modal-status-line' + (type ? ' msl-' + type : '');
}

/* ===== REPORTES PENDIENTES ===== */
document.getElementById('btnLoadReportes').addEventListener('click', loadReportesPendientes);
document.getElementById('userId').addEventListener('change', saveUserId);

async function loadReportesPendientes() {
  const btn = document.getElementById('btnLoadReportes');
  setLoading(btn, true);
  setStatus('Cargando reportes...', 'loading');

  const fechaDesde = document.getElementById('filtroDesde').value;
  const fechaHasta = document.getElementById('filtroHasta').value;

  let qs = '?';
  if (fechaDesde) qs += `fechaDesde=${fechaDesde}&`;
  if (fechaHasta) qs += `fechaHasta=${fechaHasta}&`;

  try {
    const data = await api.get(`/supervisores/mis-reportes-pendientes${qs}`);
    todosLosReportes = data;

    nombresAutocompleteTerapeutas = [
      ...new Set(data.map(r => r.nombreTerapeuta).filter(Boolean))
    ].sort();

    const inputNombre = document.getElementById('filtroTerapeutaNombre');
    if (inputNombre) inputNombre.value = '';

    renderReportesPendientes(data);
    setStatus(`${data.length} reporte(s) encontrados`, 'success');
  } catch (e) {
    setStatus(e.message, 'error');
  } finally {
    setLoading(btn, false);
  }
}

function renderReportesPendientes(lista) {
  const el = document.getElementById('reportesList');
  const total = todosLosReportes.length;

  const grid = document.getElementById('statsGrid');
  if (grid) {
    grid.style.display = '';
    document.getElementById('statTotal').textContent = total;
    document.getElementById('statVisibles').textContent = lista.length;
  }

  if (lista.length === 0) {
    el.innerHTML = `<div class="empty-state" style="padding:1.5rem;"><p>No hay reportes que coincidan</p></div>`;
    return;
  }

  el.innerHTML = lista.map(r => `
    <div class="patient-item" onclick="loadReporte(${r.idReporte}, this)">
      <div class="patient-avatar" style="background:#FEF3C7;color:#92400E;">R</div>
      <div class="patient-info">
        <div class="patient-name">${esc(r.nombreTerapeuta)}</div>
        <div class="patient-meta">Reporte #${esc(r.idReporte)} · ${fDate(r.fechaSesion)}</div>
      </div>
    </div>
  `).join('');
}

/* ===== AUTOCOMPLETE DE TERAPEUTA ===== */
window.addEventListener('DOMContentLoaded', function () {
  const inputNombre = document.getElementById('filtroTerapeutaNombre');
  const listAC      = document.getElementById('acTerapeutas');
  if (!inputNombre || !listAC) return;

  initAutocomplete(
    inputNombre,
    listAC,
    function () { return nombresAutocompleteTerapeutas; },
    function (query) {
      const q = query.trim().toLowerCase();
      const filtrados = q
        ? todosLosReportes.filter(r => r.nombreTerapeuta.toLowerCase().includes(q))
        : todosLosReportes;
      renderReportesPendientes(filtrados);
    }
  );
});

/* ===== DETALLE DE REPORTE ===== */
async function loadReporte(idReporte, itemEl) {
  if (itemEl) {
    document.querySelectorAll('.patient-item').forEach(i => i.classList.remove('active'));
    itemEl.classList.add('active');
  }
  showLoadingPanel('Cargando reporte...');

  try {
    const data = await api.get(`/reportes/${idReporte}`);
    reporteActual = data;
    renderReporte(data);
  } catch (e) {
    toast(e.message, 'error');
    showErrorPanel(e.message);
  }
}

function renderReporte(r) {
  const panel = document.getElementById('contentPanel');

  panel.innerHTML = `
    <div class="card">
      <div class="card-header">
        <h2>Reporte #${esc(r.idDocumento)}</h2>
        <div>${badge(r.estado)}</div>
      </div>
      <div class="card-body">
        <div class="info-grid" style="margin-bottom:1.25rem;">
          <div class="info-item"><div class="label">Expediente</div><div class="value">#${esc(r.idExpediente)}</div></div>
          <div class="info-item"><div class="label">Paciente</div><div class="value">${esc(r.nombrePaciente)}</div></div>
          <div class="info-item"><div class="label">Terapeuta</div><div class="value">${esc(r.nombreTerapeuta)}</div></div>
          <div class="info-item"><div class="label">Fecha de sesion</div><div class="value">${fDate(r.fechaSesion)}</div></div>
          <div class="info-item"><div class="label">Duracion</div><div class="value">${r.duracionSesion ? esc(r.duracionSesion) + ' min' : '—'}</div></div>
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
          <div class="section-title">Retroalimentacion previa (este reporte fue rechazado anteriormente)</div>
          <div class="report-text-block" style="border-color:#FECACA;background:#FEF2F2;">${esc(r.comentariosSupervisor)}</div>
        </div>` : ''}

        ${buildAccionesSupervisor(r)}
      </div>
    </div>
  `;
}

function buildAccionesSupervisor(r) {
  if (r.estado === 'PENDIENTE') {
    return `
      <div class="action-bar">
        <button class="btn btn-success" id="btnAprobar" onclick="aprobarReporte(${r.idDocumento})">
          Aprobar reporte
        </button>
        <button class="btn btn-danger" onclick="abrirModalRechazar(${r.idDocumento})">
          Rechazar reporte
        </button>
      </div>`;
  }
  if (r.estado === 'APROBADO') {
    return `<div class="action-bar"><div class="alert alert-success" style="margin:0;">Reporte aprobado.</div></div>`;
  }
  if (r.estado === 'RECHAZADO') {
    return `<div class="action-bar"><div class="alert alert-error" style="margin:0;">Reporte rechazado — el terapeuta puede corregirlo y reenviarlo.</div></div>`;
  }
  return '';
}

/* ===== APROBAR ===== */
async function aprobarReporte(idReporte) {
  const btn = document.getElementById('btnAprobar');
  setLoading(btn, true);
  try {
    const data = await api.patch(`/reportes/${idReporte}/aprobar`);
    toast(`Reporte #${idReporte} aprobado`, 'success');
    reporteActual = { ...reporteActual, estado: data.estado, fechaModificacion: data.fechaModificacion };
    renderReporte(reporteActual);
    await loadReportesPendientes();
  } catch (e) {
    toast(e.message, 'error');
    setLoading(btn, false);
  }
}

/* ===== RECHAZAR ===== */
function abrirModalRechazar(idReporte) {
  reporteIdParaRechazar = idReporte;
  document.getElementById('comentariosRechazo').value = '';
  openModal('modalRechazar');
}

document.getElementById('btnConfirmarRechazo').addEventListener('click', async () => {
  const btn = document.getElementById('btnConfirmarRechazo');
  const comentarios = document.getElementById('comentariosRechazo').value.trim();

  if (!comentarios) {
    setModalStatus('La retroalimentación es obligatoria', 'error');
    return;
  }

  setModalStatus('Enviando rechazo...', 'loading');
  setLoading(btn, true);
  try {
    const data = await api.patch(`/reportes/${reporteIdParaRechazar}/rechazar`, {
      comentariosSupervisor: comentarios,
    });
    closeModal('modalRechazar');
    setModalStatus('');
    toast(`Reporte #${reporteIdParaRechazar} rechazado`, 'info');
    reporteActual = {
      ...reporteActual,
      estado: data.estado,
      comentariosSupervisor: data.comentariosSupervisor,
      fechaModificacion: data.fechaModificacion,
    };
    renderReporte(reporteActual);
    await loadReportesPendientes();
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
