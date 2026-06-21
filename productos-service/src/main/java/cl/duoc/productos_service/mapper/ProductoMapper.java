package cl.duoc.productos_service.mapper;

import cl.duoc.productos_service.dto.ProductosDTO;
import cl.duoc.productos_service.modelo.Productos;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductoMapper {
    public ProductosDTO toDTO(Productos productos){
        if (productos==null) return null;
        ProductosDTO dto = new ProductosDTO();
        dto.setId(productos.getId());
        dto.setNombre(productos.getNombre());
        dto.setCategoria(productos.getCategoria());
        dto.setPrecio(productos.getPrecio());
        dto.setDisponible(productos.getDisponible());
        dto.setDescripcion(productos.getDescripcion());
        return dto;
    }

    public List<ProductosDTO> toDTOlist(List<Productos> listado){
        return listado.stream()
                .map(this::toDTO)
                .toList();
    }
}
