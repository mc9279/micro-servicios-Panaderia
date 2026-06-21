package cl.duoc.delivery_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data

public class DeliveryDTO {
    private Long id;
    private VentasDTO ventas;
    private LocalDateTime fechaEntrega;
    private String estado;
    private Long ventaId;
}
