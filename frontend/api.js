const API_BASE = 'http://localhost:8080/api/v1';

// URL del módulo de autenticación — ajustar al puerto real del otro equipo
const AUTH_URL = 'http://localhost:8081';

const Token = {
  get: () => localStorage.getItem('jwt'),
  set: (t) => localStorage.setItem('jwt', t),
  clear: () => localStorage.removeItem('jwt'),
};

const User = {
  get: () => JSON.parse(localStorage.getItem('user') || 'null'),
  set: (u) => localStorage.setItem('user', JSON.stringify(u)),
  clear: () => localStorage.removeItem('user'),
};

function decodeJwt(token) {
  try {
    const payload = token.split('.')[1];
    return JSON.parse(atob(payload.replace(/-/g, '+').replace(/_/g, '/')));
  } catch {
    return null;
  }
}

function recibirToken(expectedRole) {
  const params = new URLSearchParams(location.search);
  const t = params.get('token');
  if (t) {
    const payload = decodeJwt(t);
    if (payload && payload.rol === expectedRole) {
      Token.set(t);
      User.set({ id: payload.sub, nombre: payload.nombre || 'Usuario', rol: payload.rol });
      history.replaceState({}, '', location.pathname);
    }
  }
}

function checkAuth(expectedRole) {
  const token = Token.get();
  const user = User.get();
  if (!token || !user) { logout(); return false; }
  if (expectedRole && user.rol !== expectedRole) { logout(); return false; }
  return true;
}

function logout() {
  Token.clear();
  User.clear();
  window.location.href = 'index.html';
}

async function api(method, path, body = null) {
  const opts = {
    method,
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${Token.get()}`,
    },
  };
  if (body) opts.body = JSON.stringify(body);

  const res = await fetch(API_BASE + path, opts);

  if (res.status === 401) { logout(); return null; }

  if (res.status === 204 || res.headers.get('content-length') === '0') return null;

  const data = await res.json().catch(() => null);

  if (!res.ok) {
    const msg = data?.mensaje || data?.message || `Error ${res.status}`;
    throw new Error(msg);
  }

  return data;
}

function showToast(msg, type = 'success') {
  const t = document.createElement('div');
  t.className = `toast toast-${type}`;
  t.textContent = msg;
  document.body.appendChild(t);
  setTimeout(() => t.classList.add('show'), 10);
  setTimeout(() => { t.classList.remove('show'); setTimeout(() => t.remove(), 300); }, 3500);
}

function badgeEstado(estado) {
  const map = {
    CREADO: 'badge-neutral', PENDIENTE: 'badge-blue',
    APROBADO: 'badge-green', RECHAZADO: 'badge-red',
    ACTIVO: 'badge-green', ARCHIVADO: 'badge-neutral',
    PERMITIDO: 'badge-green', DENEGADO: 'badge-red',
  };
  return `<span class="badge ${map[estado] || 'badge-neutral'}">${estado}</span>`;
}

function fmtDate(str) {
  if (!str) return '—';
  return new Date(str).toLocaleDateString('es-MX', { day: '2-digit', month: 'short', year: 'numeric' });
}

function fmtDatetime(str) {
  if (!str) return '—';
  return new Date(str).toLocaleString('es-MX', { day: '2-digit', month: 'short', year: 'numeric', hour: '2-digit', minute: '2-digit' });
}
