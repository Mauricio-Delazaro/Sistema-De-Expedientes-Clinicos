const BASE_URL = 'https://sistema-de-expedientes-clinicos-tkck.onrender.com/api/v1';

/* ===== USER ID ===== */
function getUserId() {
  return document.getElementById('userId')?.value?.trim() || '';
}

function saveUserId() {
  const v = getUserId();
  if (v) localStorage.setItem('clinica_user_id', v);
}

function restoreUserId() {
  const saved = localStorage.getItem('clinica_user_id');
  const el = document.getElementById('userId');
  if (saved && el) el.value = saved;
}

/* ===== API CALLS ===== */
async function apiCall(method, path, body = null) {
  const userId = getUserId();
  if (!userId) throw new Error('Ingresa tu ID de usuario primero.');

  const headers = { 'Content-Type': 'application/json', 'X-User-Id': userId };
  const opts = { method, headers };
  if (body !== null) opts.body = JSON.stringify(body);

  let res;
  try {
    res = await fetch(`${BASE_URL}${path}`, opts);
  } catch {
    throw new Error('No se pudo conectar con el servidor. ¿Está el backend corriendo en el puerto 8080?');
  }

  const text = await res.text();
  const data = text ? JSON.parse(text) : null;

  if (!res.ok) {
    throw new Error(data?.mensaje || `Error ${res.status}: ${res.statusText}`);
  }
  return data;
}

const api = {
  get:   (path)        => apiCall('GET',   path),
  post:  (path, body)  => apiCall('POST',  path, body),
  put:   (path, body)  => apiCall('PUT',   path, body),
  patch: (path, body)  => apiCall('PATCH', path, body ?? null),
};

/* ===== TOAST ===== */
function toast(msg, type = 'info') {
  const c = document.getElementById('toastContainer');
  if (!c) return;
  const div = document.createElement('div');
  div.className = `toast toast-${type}`;
  div.textContent = msg;
  c.appendChild(div);
  setTimeout(() => {
    div.style.transition = 'opacity 0.3s';
    div.style.opacity = '0';
    setTimeout(() => div.remove(), 300);
  }, 4000);
}

/* ===== MODAL ===== */
function openModal(id) {
  document.getElementById(id)?.classList.add('open');
}
function closeModal(id) {
  document.getElementById(id)?.classList.remove('open');
}
document.addEventListener('click', (e) => {
  if (e.target.classList.contains('modal-overlay')) {
    e.target.classList.remove('open');
  }
});

/* ===== FORMATTING ===== */
function fDate(str) {
  if (!str) return '—';
  const d = new Date(str);
  return d.toLocaleDateString('es-MX', { day: '2-digit', month: 'short', year: 'numeric' });
}

function fDateTime(str) {
  if (!str) return '—';
  const d = new Date(str);
  return d.toLocaleString('es-MX', { day: '2-digit', month: 'short', year: 'numeric', hour: '2-digit', minute: '2-digit' });
}

function badge(estado) {
  const map = {
    CREADO: 'creado', PENDIENTE: 'pendiente', APROBADO: 'aprobado',
    RECHAZADO: 'rechazado', ACTIVO: 'activo', ARCHIVADO: 'archivado',
    PERMITIDO: 'permitido', DENEGADO: 'denegado',
  };
  const cls = map[estado?.toUpperCase()] ?? 'creado';
  return `<span class="badge badge-${cls}">${esc(estado)}</span>`;
}

function esc(s) {
  if (s == null) return '';
  return String(s)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}

function setLoading(btn, loading) {
  if (!btn) return;
  if (loading) {
    btn.dataset.origText = btn.innerHTML;
    btn.innerHTML = '<span class="spinner"></span>';
    btn.disabled = true;
  } else {
    btn.innerHTML = btn.dataset.origText || btn.innerHTML;
    btn.disabled = false;
  }
}

/* ===== TABS ===== */
function initTabs(containerSelector) {
  const tabs = document.querySelectorAll(`${containerSelector} .tab`);
  tabs.forEach(tab => {
    tab.addEventListener('click', () => {
      tabs.forEach(t => t.classList.remove('active'));
      tabs.forEach(t => { document.getElementById(t.dataset.tab)?.classList.remove('active'); });
      tab.classList.add('active');
      document.getElementById(tab.dataset.tab)?.classList.add('active');
    });
  });
}

window.addEventListener('DOMContentLoaded', () => {
  restoreUserId();
  document.getElementById('userId')?.addEventListener('change', saveUserId);
});

/* ===== AUTOCOMPLETE ===== */
/*
 * inputEl   – el <input type="text"> que el usuario escribe
 * listEl    – el <div class="autocomplete-list"> donde aparecen las sugerencias
 * getOpciones – function() → string[] con las opciones disponibles en ese momento
 * onCambio  – function(query) llamada cada vez que cambia el valor (al escribir o al seleccionar)
 *
 * Navegación: ↑ ↓ para moverse, Tab/Enter para seleccionar, Escape para cerrar
 * Tab sin elemento resaltado completa con la primera sugerencia visible.
 */
function initAutocomplete(inputEl, listEl, getOpciones, onCambio) {
  var idx = -1;

  function items() { return listEl.querySelectorAll('.autocomplete-item'); }

  function resaltar(i) {
    items().forEach(function (el, j) { el.classList.toggle('highlighted', j === i); });
    idx = i;
  }

  function cerrar() { listEl.classList.remove('open'); idx = -1; }

  function mostrar() {
    var q = inputEl.value.trim().toLowerCase();
    var opts = getOpciones().filter(function (o) { return o.toLowerCase().includes(q); });
    if (!q || opts.length === 0) { cerrar(); return; }
    listEl.innerHTML = opts.map(function (m) {
      return '<div class="autocomplete-item">' + esc(m) + '</div>';
    }).join('');
    items().forEach(function (el) {
      el.addEventListener('mousedown', function (e) {
        e.preventDefault();
        inputEl.value = el.textContent;
        cerrar();
        if (onCambio) onCambio(inputEl.value);
      });
    });
    listEl.classList.add('open');
    idx = -1;
  }

  inputEl.addEventListener('input', function () {
    mostrar();
    if (onCambio) onCambio(inputEl.value);
  });

  inputEl.addEventListener('keydown', function (e) {
    var its = items();
    var open = listEl.classList.contains('open');
    if (e.key === 'ArrowDown' && open) {
      e.preventDefault(); resaltar(Math.min(idx + 1, its.length - 1));
    } else if (e.key === 'ArrowUp' && open) {
      e.preventDefault(); resaltar(Math.max(idx - 1, 0));
    } else if ((e.key === 'Tab' || e.key === 'Enter') && open) {
      var target = idx >= 0 ? its[idx] : (e.key === 'Tab' ? its[0] : null);
      if (target) {
        e.preventDefault();
        inputEl.value = target.textContent;
        cerrar();
        if (onCambio) onCambio(inputEl.value);
      }
    } else if (e.key === 'Escape') {
      cerrar();
    }
  });

  /* Cerrar al perder foco, con delay para permitir clicks en el dropdown */
  inputEl.addEventListener('blur', function () { setTimeout(cerrar, 160); });
}
