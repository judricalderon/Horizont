// js/utils/session.js
document.addEventListener("DOMContentLoaded", () => {
    const user = JSON.parse(localStorage.getItem("usuario"));

    const userNameEl = document.getElementById("userName");
    const userGreetingEl = document.getElementById("userGreeting");
    const loginLink = document.getElementById("loginLink");
    const userInfo = document.getElementById("userInfo");
    const logoutBtn = document.getElementById("logoutBtn");

    if (!user) {
        // Si no está logueado y la página requiere autenticación, redirigir
        const protectedPages = ["portfolio.html", "notifications.html", "admin.html", "trading.html"];
        if (protectedPages.some(p => location.pathname.includes(p))) {
            window.location.href = "login.html";
            return;
        }
    } else {
        // Mostrar nombre de usuario donde corresponda
        if (userNameEl) userNameEl.textContent = user.nombre;
        if (userGreetingEl) userGreetingEl.textContent = `Hola, ${user.nombre}`;
        if (loginLink) loginLink.style.display = "none";
        if (userInfo) userInfo.style.display = "flex";

        // Mostrar/ocultar enlace de administración
        if (user.rol !== "admin") {
            const adminLink = document.querySelector('a[href="admin.html"]');
            if (adminLink) adminLink.style.display = "none";
        }
    }

    // Logout común
    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            localStorage.removeItem("usuario");
            window.location.href = "login.html";
        });
    }
});
