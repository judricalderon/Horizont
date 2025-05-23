document.addEventListener('DOMContentLoaded', async () => {
  const API_BASE    = 'http://localhost:8080/api/market';
  const symbols     = ['AAPL','GOOGL','MSFT','AMZN','TSLA','META','NFLX','NVDA','AMD','INTC','IBM'];
  const tbody       = document.getElementById('marketDataBody');
  let chartInstance = null;
  let currentSymbol = null;

  // 1) Cargo la tabla de símbolos
  tbody.innerHTML = `<tr><td colspan="3" style="text-align:center;color:#888">Cargando símbolos…</td></tr>`;
  try {
    const res   = await fetch(`${API_BASE}/quotes?symbols=${symbols.join(',')}`);
    if (!res.ok) throw new Error('Respuesta ' + res.status);
    const data  = await res.json();

    tbody.innerHTML = '';
    data.forEach(item => {
      const { symbol, quote } = item;
      const cp  = quote.c, pc = quote.pc;
      const pct = pc ? ((cp - pc)/pc*100).toFixed(2) : '—';
      const cls = (cp >= pc ? 'positive' : 'negative');

      const tr = document.createElement('tr');
      tr.classList.add('market-row');
      tr.innerHTML = `
        <td class="symbol-cell">${symbol}</td>
        <td class="price text-right">${cp!=null?'$'+cp.toFixed(2):'—'}</td>
        <td class="change ${cls} text-right">${pct}%</td>
      `;

      tr.addEventListener('mouseenter', () => tr.style.backgroundColor = 'rgba(0,0,0,0.05)');
      tr.addEventListener('mouseleave', () => tr.style.backgroundColor = 'transparent');

      tr.addEventListener('click', () => {
        currentSymbol = symbol;
        document.querySelectorAll('.market-row').forEach(r => r.classList.remove('selected'));
        tr.classList.add('selected');
        showSymbolChart(symbol, '1D').catch(err => showToast('Error al cargar gráfico: ' + err.message, 'error'));
      });

      tbody.appendChild(tr);
    });

    // auto-selecciona el primero
    if (symbols.length) {
      currentSymbol = symbols[0];
      document.querySelector('.market-row')?.classList.add('selected');
      showSymbolChart(currentSymbol, '1D').catch(()=>{});
    }

  } catch (err) {
    console.error('Error al cargar símbolos:', err);
    tbody.innerHTML = `<tr><td colspan="3" style="text-align:center;color:#888">No se pudieron cargar los símbolos.</td></tr>`;
    showToast('No se pudieron cargar los símbolos del mercado', 'error');
  }

// 2) Función para mostrar el gráfico con fallback de datos
async function showSymbolChart(symbol, timeframe = '1D') {
  const res1 = await fetch(`${API_BASE}/history?symbol=${symbol}&timeframe=${timeframe}`);
  if (!res1.ok) throw new Error('No se pudo cargar historial');
  let { t, c } = await res1.json();

  // fallback: datos “dummy” si viene vacío
  if (!t.length) {
    // Generar 12 timestamps espaciados cada 2 horas hacia atrás
    const now = Math.floor(Date.now() / 1000);
    t = Array.from({ length: 12 }, (_, i) => now - (11 - i) * 2 * 3600);

    // Obtener precio actual como base
    let base = 100;
    try {
      const qres = await fetch(`${API_BASE}/quotes?symbols=${symbol}`);
      if (qres.ok) {
        const qd = await qres.json();
        base = qd[0]?.quote.c || base;
      }
    } catch { /* silent */ }

    // Generar valores con pequeña variación aleatoria
    c = [];
    let current = base;
    for (let i = 0; i < t.length; i++) {
      // variación aleatoria de hasta ±1.5%
      const changePct = (Math.random() - 0.5) * 0.03;
      current = parseFloat((current * (1 + changePct)).toFixed(2));
      c.push(current);
    }
  }

  // Preparar labels de horas:minutos
  const labels = t.map(ts => {
    const d = new Date(ts * 1000);
    return d.getHours() + ':' + String(d.getMinutes()).padStart(2, '0');
  });

  // Configuración del dataset
  const data = {
    labels,
    datasets: [{
      label: `${symbol} precio (USD)`,
      data: c,
      fill: false,
      tension: 0.1
    }]
  };

  // Renderizar el chart
  if (chartInstance) chartInstance.destroy();
  const ctx = document.getElementById('symbolChart').getContext('2d');
  chartInstance = new Chart(ctx, {
    type: 'line',
    data,
    options: {
      scales: {
        x: { display: true, title: { display: true, text: 'Hora' } },
        y: { display: true, title: { display: true, text: 'Precio (USD)' } }
      }
    }
  });

  // Actualizar encabezado
  document.getElementById('selectedSymbolHeader')
    .textContent = `${symbol} — ${timeframe === '1D' ? 'Últimas 24h' : timeframe}`;
}


  // 3) Timeframe buttons
  document.querySelectorAll('.btn-timeframe').forEach(btn => {
    btn.addEventListener('click', () => {
      document.querySelectorAll('.btn-timeframe').forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      if (currentSymbol) {
        showSymbolChart(currentSymbol, btn.textContent)
          .catch(err => showToast('Error al cambiar timeframe: ' + err.message, 'error'));
      }
    });
  });

  // 4) Filtrado por búsqueda
  document.getElementById('searchInput').addEventListener('input', () => {
    const filter = document.getElementById('searchInput').value.toUpperCase();
    document.querySelectorAll('.market-row').forEach(row => {
      const sym = row.querySelector('.symbol-cell').textContent.toUpperCase();
      row.style.display = sym.includes(filter) ? '' : 'none';
    });
  });
});