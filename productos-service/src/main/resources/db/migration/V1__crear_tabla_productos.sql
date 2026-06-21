CREATE TABLE productos (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(50) NOT NULL,
                           categoria VARCHAR(50) NOT NULL,
                           precio DOUBLE NOT NULL,
                           disponible BOOLEAN NOT NULL,
                           descripcion VARCHAR(255)
);