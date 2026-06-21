package cl.duoc.ventas_service.dto;

import lombok.Data;

@Data

public class VentasDTO {
    private Long id;

    private ClienteDTO cliente;
    private ProductoDTO producto;

    private Integer cantidad;
    private Double total;
    private String metodoPago;
    private String estado;

    private Long clienteId;
    private Long productoId;
}
