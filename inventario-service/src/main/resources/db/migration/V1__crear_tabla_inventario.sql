CREATE TABLE inventario (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,

                            nombre_ingrediente VARCHAR(100) NOT NULL,

                            stock DOUBLE NOT NULL,

                            unidad_medida VARCHAR(50) NOT NULL,

                            stock_minimo DOUBLE NOT NULL,

                            fecha_actualizacion DATE NOT NULL,

                            producto_id BIGINT,

                            panadero_id BIGINT
);