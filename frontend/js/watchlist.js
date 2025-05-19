
document.addEventListener('DOMContentLoaded', () => {
    const API         = 'http://localhost:8080/api/market';
    const user        = JSON.parse(localStorage.getItem('usuario') || '{}');
    const isPremium   = !!user.esPremium;
    const usuarioId   = user.id;
    if (!usuarioId) {
        alert('Debes iniciar sesión');
        return window.location = 'login.html';
    }

    // Elementos
    const lockedEl    = document.getElementById('watchlistLocked');
    const summaryEl   = document.getElementById('watchlistSummary');
    const listEl      = document.getElementById('watchlistList');
    const bodyEl      = document.getElementById('watchlistBody');
    const modal       = document.getElementById('addStockModal');
    const btnOpen     = document.getElementById('addStockBtn');
    const btnClose    = document.getElementById('closeModalBtn');
    const btnCancel   = document.getElementById('cancelAddStock');
    const btnConfirm  = document.getElementById('confirmAddStock');
    const symbolInput = document.getElementById('stockSymbol');

    // Si NO es premium, muestro bloqueo y salgo
    if (!isPremium) {
        lockedEl.style.display = 'flex';
        summaryEl.style.display = 'none';
        listEl.style.display = 'none';
        btnOpen.style.display = 'none';
        return;
    }

    // Modal: abrir / cerrar y accesibilidad
    btnOpen.onclick = () => {
        modal.classList.add('show');
        symbolInput.value = '';
        symbolInput.focus();
    };
    [btnClose, btnCancel].forEach(btn =>
        btn.onclick = () => modal.classList.remove('show')
    );
    modal.addEventListener('keydown', e => {
        if (e.key === 'Escape') modal.classList.remove('show');
    });

    // Cargar Watchlist
    async function loadWL() {
        // Feedback: deshabilito "Agregar Acción" y muestro texto
        btnOpen.disabled = true;
        btnOpen.textContent = 'Cargando...';

        try {
            const res = await fetch(`${API}/watchlist?usuarioId=${usuarioId}`);
            if (!res.ok) throw new Error(res.status);
            const data = await res.json();

            if (!Array.isArray(data) || data.length === 0) {
                bodyEl.innerHTML = `
            <tr><td colspan="4" style="text-align:center;color:#888">
              Tu Watchlist está vacía. Agrega tu primera acción.
            </td></tr>`;
                summaryEl.innerHTML = '';
            } else {
                const valid = data.filter(item =>
                    item.quote && typeof item.quote.c === 'number' && typeof item.quote.pc === 'number'
                );
                bodyEl.innerHTML = valid.map(({symbol, quote}) => {
                    const cp  = quote.c, pc = quote.pc;
                    const pct = ((cp - pc)/pc*100).toFixed(2);
                    const cls = cp >= pc ? 'positive' : 'negative';
                    return `
              <tr>
                <td>${symbol}</td>
                <td class="price">$${cp.toFixed(2)}</td>
                <td class="change ${cls}">${pct}%</td>
                <td class="actions">
                  <button class="btn-remove" data-symbol="${symbol}">
                    <span class="material-icons">delete</span>
                  </button>
                </td>
              </tr>`;
                }).join('');

                // Resumen
                const total = valid.length;
                const valor = valid.reduce((s,i)=> s + i.quote.c, 0);
                const perf  = (valid.reduce((s,i)=> s + (i.quote.c - i.quote.pc)/i.quote.pc, 0)/total*100).toFixed(2);
                summaryEl.innerHTML = `
            <div class="summary-card">
              <h3>Total Acciones: <span>${total}</span></h3>
              <p>Valor total estimado: <strong>$${valor.toFixed(2)}</strong></p>
            </div>
            <div class="summary-performance">
              <p class="${perf>=0?'positive':'negative'}">
                ${perf>=0?'+':''}${perf}% diario
              </p>
            </div>`;

                // Conectar botones de Borrar con animación
                document.querySelectorAll('.btn-remove').forEach(btn => {
                    btn.onclick = async () => {
                        btn.disabled = true;
                        const row = btn.closest('tr');
                        const sym = btn.dataset.symbol;
                        try {
                            const delRes = await fetch(`${API}/watchlist?usuarioId=${usuarioId}`, {
                                method: 'DELETE',
                                headers:{ 'Content-Type':'application/json' },
                                body: JSON.stringify([sym])
                            });
                            if (delRes.status === 404) {
                                alert('Acción no encontrada en tu Watchlist.');
                                btn.disabled = false;
                                return;
                            } else if (!delRes.ok) {
                                throw new Error(delRes.status);
                            }
                            // Fade-out
                            row.style.transition = 'opacity 0.3s';
                            row.style.opacity = 0;
                            setTimeout(loadWL, 300);
                        } catch(err) {
                            console.error(err);
                            alert('No se pudo eliminar la acción. Revisa la consola.');
                            btn.disabled = false;
                        }
                    };
                });
            }
        } catch(err) {
            console.error('Error cargando watchlist:', err);
            alert('Error cargando la watchlist. Revisa la consola.');
        } finally {
            // Restaurar botón
            btnOpen.disabled = false;
            btnOpen.innerHTML = `<span class="material-icons">add</span> Agregar Acción`;
        }
    }

    // Añadir acción con validación y manejo de errores
    btnConfirm.onclick = async () => {
        const sym = symbolInput.value.trim().toUpperCase();
        if (!/^[A-Z]{1,5}$/.test(sym)) {
            return alert('El símbolo debe ser entre 1 y 5 letras mayúsculas (ej: AAPL, BTC).');
        }
        btnConfirm.disabled = true;
        btnConfirm.textContent = 'Agregando...';
        try {
            const res = await fetch(`${API}/watchlist?usuarioId=${usuarioId}`, {
                method: 'POST',
                headers:{ 'Content-Type':'application/json' },
                body: JSON.stringify([sym])
            });
            if (res.status === 400) {
                alert('Solo usuarios Premium pueden añadir Watchlists.');
            } else if (res.status === 404) {
                alert('Símbolo no encontrado.');
            } else if (res.status === 409) {
                alert('Este símbolo ya está en tu Watchlist.');
            } else if (!res.ok) {
                throw new Error(res.status);
            } else {
                modal.classList.remove('show');
                await loadWL();
            }
        } catch(err) {
            console.error('Error agregando:', err);
            alert('No se pudo agregar la acción. Revisa la consola.');
        } finally {
            btnConfirm.disabled = false;
            btnConfirm.textContent = 'Agregar';
        }
    };

    // Carga inicial
    loadWL();
});

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.stock-option').forEach(button => {
        button.addEventListener('click', () => {
            const symbol = button.getAttribute('data-symbol');
            document.getElementById('stockSymbol').value = symbol;
        });
    });
});
