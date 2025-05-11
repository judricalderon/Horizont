// ... existing code ...
document.getElementById('registrationForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const password = document.getElementById('password').value;
    
    // Remove all password complexity checks
    if (password.length === 0) {
        alert('La contraseña es requerida');
        return;
    }
    
    // Rest of your registration logic...
    // (Keep only the basic "password required" check)
});
// ... existing code ...