CREATE TABLE delivery (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          direccion_entrega VARCHAR(255) NOT NULL,
                          fecha_entrega DATETIME,
                          estado VARCHAR(100),
                          venta_id BIGINT
);