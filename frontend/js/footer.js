export function initializeFooter() {
    const footer = document.querySelector('.main-footer');
    if (!footer) return;

    // Add current year to copyright
    const copyrightText = footer.querySelector('.footer-bottom p');
    if (copyrightText) {
        const currentYear = new Date().getFullYear();
        copyrightText.innerHTML = `&copy; ${currentYear} Acciones El Bosque. Todos los derechos reservados.`;
    }

    // Add social media links
    const socialLinks = document.createElement('div');
    socialLinks.className = 'social-links';
    socialLinks.innerHTML = `
        <a href="#" class="social-link"><span class="material-icons">facebook</span></a>
        <a href="#" class="social-link"><span class="material-icons">twitter</span></a>
        <a href="#" class="social-link"><span class="material-icons">linkedin</span></a>
    `;
    footer.querySelector('.footer-content').appendChild(socialLinks);

    // Add newsletter subscription
    const newsletterSection = document.createElement('div');
    newsletterSection.className = 'footer-section';
    newsletterSection.innerHTML = `
        <h4>Newsletter</h4>
        <form class="newsletter-form">
            <input type="email" placeholder="Tu email" required>
            <button type="submit">Suscribirse</button>
        </form>
    `;
    footer.querySelector('.footer-content').appendChild(newsletterSection);

    // Handle newsletter submission
    const newsletterForm = newsletterSection.querySelector('.newsletter-form');
    newsletterForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const email = newsletterForm.querySelector('input[type="email"]').value;
        // TODO: Implement newsletter subscription
        console.log('Newsletter subscription:', email);
        newsletterForm.reset();
    });
} 