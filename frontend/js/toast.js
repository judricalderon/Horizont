export function showToast(message, type = 'success') {
  const container = document.getElementById('toast-container') ||
    Object.assign(document.body.appendChild(document.createElement('div')), {
      id: 'toast-container'
    });

  const toast = document.createElement('div');
  toast.className = `toast ${type}`;
  toast.innerHTML = `
    <span>${message}</span>
    <button class="close-btn">&times;</button>
  `;

  // Cerrar manualmente
  toast.querySelector('.close-btn').addEventListener('click', () => {
    toast.remove();
  });

  // Remover después de animación de salida
  toast.addEventListener('animationend', (e) => {
    if (e.animationName === 'fadeOut') {
      toast.remove();
    }
  });

  container.appendChild(toast);
}
