package cl.duoc.ventas_service.dto;

import lombok.Data;

@Data

public class ProductoDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private Double precio;
    private Boolean disponible;
}
