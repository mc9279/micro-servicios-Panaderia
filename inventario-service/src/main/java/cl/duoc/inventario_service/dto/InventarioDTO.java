package cl.duoc.inventario_service.dto;

import lombok.Data;

@Data
public class InventarioDTO {
    private Long id;
    private Long productoId;
    private Long panaderoId;
    private double stock;
    private  PanaderoDTO panadero;
    private ProductosDTO producto;
}
