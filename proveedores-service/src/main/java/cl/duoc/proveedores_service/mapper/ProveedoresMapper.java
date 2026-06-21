package cl.duoc.proveedores_service.mapper;

import cl.duoc.proveedores_service.dto.ProveedoresDTO;
import cl.duoc.proveedores_service.modelo.Proveedores;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Component

public class ProveedoresMapper {
    public ProveedoresDTO toDTO(Proveedores productos){
        if (productos==null) return null;
        ProveedoresDTO dto = new ProveedoresDTO();
        dto.setId(productos.getId());
        dto.setNombreEmpresa(productos.getNombreEmpresa());
        dto.setTelefono(productos.getTelefono());
        dto.setEmail(productos.getEmail());
        return dto;
    }

    public List<ProveedoresDTO> toDTOlist(List<Proveedores> listado){
        return listado.stream()
                .map(this::toDTO)
                .toList();
    }
}
