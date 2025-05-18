// js/include-components.js
async function includeHTML(id, url) {
    const el = document.getElementById(id);
    if (!el) return;
    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error(`Error ${res.status}`);
        el.innerHTML = await res.text();
    } catch (e) {
        console.error(`No se pudo cargar ${url}:`, e);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    includeHTML('header-placeholder', '/frontend/html/pages/header.html');
    includeHTML('footer-placeholder', '/frontend/html/pages/footer.html');
});


// Función para incluir un fragmento y devolver promesa
async function includeHTML(id, url) {
    const el = document.getElementById(id);
    if (!el) return;
    const res = await fetch(url);
    if (!res.ok) throw new Error(`Error ${res.status} al cargar ${url}`);
    el.innerHTML = await res.text();
}

// Inicializa tus componentes y luego la sesión
document.addEventListener('DOMContentLoaded', async () => {
    try {
        await includeHTML('header-placeholder', '/components/header.html');
        await includeHTML('footer-placeholder', '/components/footer.html');

        // Una vez inyectado el header, carga el script de sesión
        const script = document.createElement('script');
        script.src = '/js/session.js';    // ruta a tu session.js
        document.body.appendChild(script);
    } catch (e) {
        console.error(e);
    }
});
