-- Insertar usuarios de prueba
INSERT IGNORE INTO users (username, password, email, full_name, role)
VALUES 
    ('admin', '12345', 'admin@elbosque.edu.co', 'Administrador Sistema', 'ADMIN'),
    ('profesor', '12345', 'profesor@elbosque.edu.co', 'Profesor Demo', 'TEACHER'),
    ('estudiante', '12345', 'estudiante@elbosque.edu.co', 'Estudiante Demo', 'STUDENT'),
    ('monitor', '12345', 'monitor@elbosque.edu.co', 'Monitor Demo', 'MONITOR'),
    ('invitado', '12345', 'invitado@elbosque.edu.co', 'Usuario Invitado', 'GUEST'); 