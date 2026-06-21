package cl.duoc.proveedores_service.dto;

import lombok.Data;

@Data
public class ProveedoresDTO {
    private Long id;
    private String nombreEmpresa;
    private String email;
    private String telefono;
}
