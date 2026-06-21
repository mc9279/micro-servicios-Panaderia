package cl.duoc.delivery_service.dto;

import lombok.Data;

@Data

public class VentasDTO {
    private Long id;
    private Integer cantidad;
    private String metodoPago;
}
