document.addEventListener('DOMContentLoaded', () => {
  const user = JSON.parse(localStorage.getItem('usuario'));
  if (!user) return window.location.href = 'index.html';
  const userId = user.id;

  const symbols = ['AAPL','GOOGL','MSFT','AMZN','TSLA', 'NFLX','NVDA','AMD','INTC','IBM'];
  const symbolSelect = document.getElementById('symbolSelect');
  const priceInput    = document.getElementById('priceInput');
  const quantityInput = document.getElementById('quantityInput');
  const orderTypeSelect = document.getElementById('orderTypeSelect');
  const executeBtn    = document.getElementById('executeBtn');

  symbols.forEach(sym => {
    const opt = document.createElement('option');
    opt.value = sym;
    opt.textContent = sym;
    symbolSelect.appendChild(opt);
  });

  async function updatePrice() {
    const symbol = symbolSelect.value;
    try {
      const res = await fetch(`http://localhost:8080/api/trade/preview/${userId}`, {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({ simbolo: symbol, cantidad: 1, tipo: 'COMPRA' })
      });
      if (!res.ok) throw new Error(res.statusText);
      const data = await res.json();
      priceInput.value = data.precioActual.toFixed(2);
    } catch (e) {
      console.error('Error fetching price:', e);
      priceInput.value = '—';
    }
  }

  symbolSelect.addEventListener('change', updatePrice);
  updatePrice();

  executeBtn.addEventListener('click', async () => {
    const symbol = symbolSelect.value;
    const qty    = parseInt(quantityInput.value, 10);
    const type   = orderTypeSelect.value;
    if (!qty || qty <= 0) {
    showToast('Cantidad inválida', 'error');
    return;
    }

    try {
      const res = await fetch(`http://localhost:8080/api/trade/execute/${userId}`, {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({ simbolo: symbol, cantidad: qty, tipo: type })
      });
      if (!res.ok) throw new Error(await res.text());
      const result = await res.json();
      showToast(`Orden ejecutada: ${type} ${symbol} x${qty} a $${result.precioEjecutado.toFixed(2)}`, 'success');
      // Optionally refresh history and positions
    } catch (e) {
      showToast('Error al ejecutar orden: ' + e.message, 'error');
    }
  });
});