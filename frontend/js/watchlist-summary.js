// watchlist-summary.js
document.addEventListener('DOMContentLoaded', () => {
    const isPremium = window.isPremium ?? false;
    const container = document.getElementById('watchlist-content');
    if (!container) return;

    const url = isPremium
        // Si es premium, cargar la versión completa
        ? '/frontend/html/pages/watchlist-summary-premium.html'
        // Si no es premium, cargar una versión reducida
        : '/frontend/html/pages/watchlist-summary.html';
    // Cargar el contenido de la Watchlist


    fetch(url)
        .then(res => res.text())
        .then(html => {
            container.innerHTML = html;

            // Si no es premium, bloquear acceso al link completo
            const link = document.getElementById('watchlist-link');
            if (link && !isPremium) {
                link.addEventListener('click', evt => {
                    evt.preventDefault();
                    alert('🔒 Solo usuarios Premium pueden ver la Watchlist completa.');
                });
            }
        })
        .catch(err => {
            console.error('Error cargando la Watchlist:', err);
        });
});

