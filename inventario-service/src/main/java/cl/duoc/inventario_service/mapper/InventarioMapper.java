package cl.duoc.inventario_service.mapper;

import cl.duoc.inventario_service.cliente.PanaderoCliente;
import cl.duoc.inventario_service.cliente.ProductoCliente;
import cl.duoc.inventario_service.dto.InventarioDTO;
import cl.duoc.inventario_service.modelo.Inventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class InventarioMapper {
    @Autowired
    private ProductoCliente productoCliente;

    @Autowired
    private PanaderoCliente panaderoCliente;


    public InventarioDTO toDTO(Inventario inventario){

        if(inventario==null) return null;

        InventarioDTO dto = new InventarioDTO();

        dto.setId(inventario.getId());
        dto.setStock(inventario.getStock());
        dto.setProductoId(inventario.getProductoId());
        dto.setPanaderoId(inventario.getPanaderoId());
        dto.setPanadero(panaderoCliente.buscarPanaderoPorId(inventario.getPanaderoId()));
        dto.setProducto(productoCliente.buscarProductoPorID(inventario.getProductoId()));
        return dto;

    }
    public Inventario toEntity(InventarioDTO dto){

        Inventario inventario = new Inventario();
        inventario.setId(dto.getId());
        inventario.setStock(dto.getStock());
        inventario.setProductoId(dto.getProductoId());
        inventario.setPanaderoId(dto.getPanaderoId());
        return inventario;
    }



    public List<InventarioDTO>
    toListDTO(List<Inventario> lista){

        return lista.stream()
                .map(this::toDTO)
                .toList();
    }
}
