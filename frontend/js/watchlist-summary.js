// watchlist-summary.js
(function(){
  const API_BASE    = 'http://localhost:8080/api/market';
  const usuarioId   = window.usuario?.id;
  const isPremium   = window.isPremium ?? true;
  const container   = document.getElementById('watchlist-content');
  if (!container) return;

  // 1) Inyectar el partial según tipo de usuario
  const partialUrl = isPremium
    ? '/frontend/html/pages/watchlist-summary-premium.html'
    : '/frontend/html/pages/watchlist-summary.html';

  fetch(partialUrl)
    .then(res => {
      if (!res.ok) throw new Error('Partial no encontrado: ' + res.status);
      return res.text();
    })
    .then(html => {
      container.innerHTML = html;

      if (isPremium) {
        // Si es premium, cargamos los datos reales
        renderSummary();
      } else {
        // Si no, bloqueo del link
        const link = document.getElementById('watchlist-link');
        link?.addEventListener('click', e => {
          e.preventDefault();
          alert('🔒 Solo usuarios Premium pueden ver tu Watchlist completa.');
        });
      }
    })
    .catch(err => {
      console.error('Error inyectando partial:', err);
      container.innerHTML = '<p class="error">No se pudo cargar el resumen.</p>';
    });

  // 2) Función para traer y renderizar la Watchlist
  async function renderSummary() {
    const itemsContainer = document.getElementById('watchlist-summary-items');
    if (!itemsContainer) return;

    itemsContainer.innerHTML = '<p class="loading">Cargando tus favoritos…</p>';

    try {
      const res  = await fetch(`${API_BASE}/watchlist?usuarioId=${usuarioId}`);
      if (!res.ok) throw new Error('HTTP ' + res.status);
      const data = await res.json();

      if (!Array.isArray(data) || data.length === 0) {
        itemsContainer.innerHTML = '<p>No tienes acciones en tu Watchlist.</p>';
        return;
      }

      // Limpiamos y pintamos cada elemento
      itemsContainer.innerHTML = '';
      data.forEach(item => {
        // Detectar formato de campos
        const cp = typeof item.quote.currentPrice === 'number'
          ? item.quote.currentPrice
          : item.quote.c;    // fallback a .c
        const pc = typeof item.quote.previousClosePrice === 'number'
          ? item.quote.previousClosePrice
          : item.quote.pc;   // fallback a .pc

        const diff = cp - pc;
        const pct  = ((diff / pc) * 100).toFixed(2);

        const div = document.createElement('div');
        div.className = 'watchlist-item';
        div.innerHTML = `
          <span class="symbol">${item.symbol}</span>
          <span class="price">$${cp.toFixed(2)}</span>
          <span class="change ${diff >= 0 ? 'positive' : 'negative'}">
            ${diff >= 0 ? '+' : ''}${pct}%
          </span>`;
        itemsContainer.appendChild(div);
      });

    } catch(err) {
      console.error('Error cargando datos Watchlist:', err);
      itemsContainer.innerHTML = '<p class="error">No se pudo cargar tus favoritos.</p>';
    }
  }

})();
