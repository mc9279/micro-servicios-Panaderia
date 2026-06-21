package cl.duoc.productos_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductosDTO {
    private Long id;


    private String nombre;

    private String categoria;

    private Double precio;
    private Boolean disponible;

    private String descripcion;
}
