import { showToast } from '../js/toast.js';

document.addEventListener('DOMContentLoaded', () => {
  const user = JSON.parse(localStorage.getItem('usuario'));
  if (!user) return window.location.href = 'index.html';
  const userId = user.id;

  const orderForm = document.getElementById('orderForm');
  const inputSymbol   = orderForm.elements['symbol'];
  const selectType    = orderForm.elements['orderType'];
  const inputQuantity = orderForm.elements['quantity'];
  const inputPrice    = orderForm.elements['price'];
  const totalPreview  = document.getElementById('totalPreview');

  const orderHistoryDiv = document.querySelector('.order-history');
  const positionsDiv    = document.querySelector('.positions-list');

  async function loadOrderHistory() {
    const res = await fetch(`http://localhost:8080/api/trade/list/${userId}`);
    const orders = await res.json();
    orderHistoryDiv.innerHTML = orders.map(o => `
      <div class="order-entry">
        <span>${o.fecha?.slice(0,19).replace('T',' ')}</span>
        <strong>${o.tipo}</strong> ${o.simbolo} x${o.cantidad}
        @ $${o.precioEjecutado.toFixed(2)} → <em>${o.estado}</em>
      </div>
    `).join('');
  }

  async function loadPositions() {
    const res = await fetch(`http://localhost:8080/api/trade/list/${userId}`);
    const orders = await res.json();
    const pos = orders.reduce((acc, o) => {
      const sign = o.tipo === 'COMPRA' ? 1 : -1;
      acc[o.simbolo] = (acc[o.simbolo]||0) + sign*o.cantidad;
      return acc;
    }, {});
    positionsDiv.innerHTML = Object.entries(pos)
      .filter(([sym,qty]) => qty !== 0)
      .map(([sym,qty]) => `
        <div class="position-entry">
          <span>${sym}</span>
          <strong>${qty}</strong>
        </div>
      `).join('') || '<p>No tienes posiciones abiertas.</p>';
  }

async function updateTotalPreview() {
  const symbol = inputSymbol.value.trim().toUpperCase();
  const qty = parseInt(inputQuantity.value, 10);
  const type = selectType.value;

  if (!symbol || !qty || qty <= 0) {
    if (totalPreview) {
      totalPreview.textContent = 'Total estimado: $0.00';
      totalPreview.className = 'total-preview';
    }
    return;
  }

  try {
    const previewRes = await fetch(`http://localhost:8080/api/trade/preview/${userId}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ simbolo: symbol, cantidad: qty, tipo: type })
    });

    if (!previewRes.ok) throw new Error(await previewRes.text());

    const preview = await previewRes.json();
    inputPrice.value = preview.precioActual.toFixed(2);
    const total = qty * preview.precioActual;

    if (totalPreview) {
      totalPreview.textContent = `Total estimado: $${total.toFixed(2)}`;
      totalPreview.className = `total-preview ${type === 'COMPRA' ? 'total-compra' : 'total-venta'}`;
    }
  } catch (e) {
    if (totalPreview) {
      totalPreview.textContent = 'Error al calcular el total';
      totalPreview.className = 'total-preview';
    }
  }
}


  async function handleOrder(side) {
    const symbol = inputSymbol.value.trim().toUpperCase();
    const qty    = parseInt(inputQuantity.value, 10);
    if (!symbol || !qty || qty <= 0) {
      showToast('Símbolo y cantidad válidos.', 'error');
      return;
    }

    // Preview
    let preview;
    try {
      const previewRes = await fetch(`http://localhost:8080/api/trade/preview/${userId}`, {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({ simbolo: symbol, cantidad: qty, tipo: side })
      });
      if (!previewRes.ok) {
        const errText = await previewRes.text();
        let friendlyMessage = 'Error al obtener el precio de la orden.';
        try {
          const errJson = JSON.parse(errText);
          if (errJson.mensaje && errJson.mensaje.toLowerCase().includes('fondos insuficientes')) {
            friendlyMessage = 'No tienes fondos suficientes para realizar esta operación.';
          } else if (errJson.mensaje) {
            friendlyMessage = errJson.mensaje;
          }
        } catch {}
        throw new Error(friendlyMessage);
      }
      preview = await previewRes.json();
      inputPrice.value = preview.precioActual.toFixed(2);
      if (totalPreview) {
        const total = qty * preview.precioActual;
        totalPreview.textContent = `Total estimado: $${total.toFixed(2)}`;
      }
    } catch (e) {
      showToast(e.message, 'error');
      return;
    }

    // Execute
    let exec;
    try {
      const execRes = await fetch(`http://localhost:8080/api/trade/execute/${userId}`, {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({ simbolo: symbol, cantidad: qty, tipo: side })
      });
      if (!execRes.ok) throw new Error(await execRes.text());
      exec = await execRes.json();
      showToast(`Orden ${exec.tipo} ${exec.simbolo} x${exec.cantidad} a $${exec.precioEjecutado.toFixed(2)}`, 'success');
    } catch (e) {
      const rawMessage = e.message || '';
      let friendlyMessage = 'Error al ejecutar la orden.';

      if (rawMessage.toLowerCase().includes('fondos insuficientes')) {
        friendlyMessage = 'No tienes fondos suficientes para realizar esta operación.';
      } else if (rawMessage.toLowerCase().includes('cantidad inválida')) {
        friendlyMessage = 'La cantidad ingresada no es válida.';
      } else {
        friendlyMessage = 'Ocurrió un error al ejecutar la orden. Intenta nuevamente.';
      }

      showToast(friendlyMessage, 'error');
      return;
    }

    await loadOrderHistory();
    await loadPositions();
  }

  // Evento principal
  document.getElementById('executeBtn').addEventListener('click', () => handleOrder(selectType.value));

  // Eventos de actualización en tiempo real
  inputSymbol.addEventListener('change', updateTotalPreview);
  inputQuantity.addEventListener('input', updateTotalPreview);
  selectType.addEventListener('change', updateTotalPreview);

  loadOrderHistory();
  loadPositions();
  updateTotalPreview(); // para precargar si hay datos ya cargados
});
