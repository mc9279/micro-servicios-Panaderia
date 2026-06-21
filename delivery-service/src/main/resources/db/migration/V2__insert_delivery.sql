INSERT INTO delivery
(direccion_entrega, fecha_entrega, estado, venta_id)
VALUES
    ('Av. Siempre Viva 123', NOW(), 'Pendiente', 1),
    ('Los Robles 456', NOW(), 'En camino', 2),
    ('Calle Central 789', NOW(), 'Entregado', 3);