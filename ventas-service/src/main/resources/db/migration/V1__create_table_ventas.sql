CREATE TABLE ventas (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,

                        cantidad INT NOT NULL,

                        total DOUBLE NOT NULL,

                        metodo_pago VARCHAR(50) NOT NULL,

                        estado VARCHAR(50) NOT NULL,

                        cliente_id BIGINT NOT NULL,

                        producto_id BIGINT NOT NULL
);