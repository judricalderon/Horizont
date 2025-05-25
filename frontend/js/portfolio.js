document.addEventListener('DOMContentLoaded', async () => {
  const user = JSON.parse(localStorage.getItem('usuario'));
  if (!user) return;

  const userId = user.id;
  const tableBody = document.getElementById('ordersHistoryTableBody');

  try {
    const res = await fetch(`http://localhost:8080/api/trade/list/${userId}`);
    if (!res.ok) throw new Error(`Error ${res.status} al obtener historial`);

    const orders = await res.json();
    console.log('Historial de órdenes:', orders);

    tableBody.innerHTML = orders.map(o => {
      // Obtener fecha/hora actual del navegador
      const now = new Date();
      const fechaVal = now.toLocaleDateString('es-ES') + ' ' + now.toLocaleTimeString('es-ES');

      // Puedes usar también timestamp numérico:
      const tsVal = now.getTime();

      const precio = typeof o.precioEjecutado === 'number'
        ? o.precioEjecutado.toFixed(2)
        : '—';

      const total = o.cantidad && typeof o.precioEjecutado === 'number'
        ? (o.cantidad * o.precioEjecutado).toFixed(2)
        : '—';

      return `
        <tr>
          <td>${fechaVal}</td>
          <td>${o.tipo || '—'}</td>
          <td>${o.estado || '—'}</td>
          <td>${o.simbolo || '—'}</td>
          <td>${o.cantidad || '—'}</td>
          <td>$${precio}</td>
          <td>$${total}</td>
          <td>${tsVal}</td>
        </tr>
      `;
    }).join('');

  } catch (e) {
    console.error('Error al cargar historial de órdenes:', e);
    tableBody.innerHTML = '<tr><td colspan="8">No se pudo cargar el historial de órdenes.</td></tr>';
  }
});

document.getElementById('searchInput').addEventListener('input', function () {
  const filter = this.value.toLowerCase();
  const rows = document.querySelectorAll('#ordersHistoryTableBody tr');
  rows.forEach(row => {
    const text = row.textContent.toLowerCase();
    row.style.display = text.includes(filter) ? '' : 'none';
  });
});

(async () => {
  const user = JSON.parse(localStorage.getItem('usuario'));
  if (!user) return;

  try {
    const res = await fetch(`http://localhost:8080/api/trade/list/${user.id}`);
    if (!res.ok) throw new Error(`Error ${res.status} al obtener historial`);
    const orders = await res.json();

    const summary = {};

    orders.forEach(o => {
      const simbolo = o.simbolo;
      const tipo = o.tipo?.toUpperCase(); // COMPRA o VENTA
      const cantidad = Number(o.cantidad);
      const precio = Number(o.precioEjecutado);

      const subtotal = cantidad * precio;
      const comisionCalculada = subtotal * 0.005; // 0.5% comisión

      if (!summary[simbolo]) {
        summary[simbolo] = {
          simbolo,
          compras: 0,
          ventas: 0,
          comisiones: 0
        };
      }

      if (tipo === 'COMPRA') {
        summary[simbolo].compras += subtotal;
      } else if (tipo === 'VENTA') {
        summary[simbolo].ventas += subtotal;
      }

      summary[simbolo].comisiones += comisionCalculada;
    });

    const gainsData = Object.values(summary).map(item => {
      const ganancia = item.ventas - item.compras - item.comisiones;
      return {
        symbol: item.simbolo,
        gain: ganancia,
        commission: item.comisiones,
        broker: "Horizont"
      };
    });

    const tbody = document.getElementById("gainsCommissionsTableBody");
    tbody.innerHTML = gainsData.map(g => `
      <tr>
        <td>${g.symbol}</td>
        <td class="${g.gain >= 0 ? 'gain-positive' : 'gain-negative'}">
          ${g.gain.toFixed(2)} USD
        </td>
        <td>${g.commission.toFixed(2)} USD</td>
        <td>${g.broker}</td>
        <td class="hide-column">-</td>
      </tr>
    `).join("");

  } catch (e) {
    console.error('Error al cargar ganancias y comisiones:', e);
    const tbody = document.getElementById("gainsCommissionsTableBody");
    tbody.innerHTML = '<tr><td colspan="5">No se pudo cargar la información.</td></tr>';
  }
})();

