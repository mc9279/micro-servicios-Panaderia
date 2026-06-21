package cl.duoc.ventas_service.mapper;

import cl.duoc.ventas_service.Cliente.ClienteClientes;
import cl.duoc.ventas_service.Cliente.ProductoClientes;
import cl.duoc.ventas_service.dto.VentasDTO;
import cl.duoc.ventas_service.modelo.Ventas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VentasMapper {

    @Autowired
    private ClienteClientes clienteClientes;

    @Autowired
    private ProductoClientes productoClientes;

    public VentasDTO toDTO(Ventas ventas){

        if(ventas == null) return null;

        VentasDTO dto = new VentasDTO();

        dto.setId(ventas.getId());
        dto.setCantidad(ventas.getCantidad());
        dto.setTotal(ventas.getTotal());
        dto.setMetodoPago(ventas.getMetodoPago());
        dto.setEstado(ventas.getEstado());

        dto.setClienteId(ventas.getClienteId());
        dto.setProductoId(ventas.getProductoId());

        dto.setCliente(clienteClientes.buscarClientePorId(ventas.getClienteId()));

        dto.setProducto(productoClientes.buscarProductoPorId(ventas.getProductoId()));

        return dto;
    }

    public Ventas toEntity(VentasDTO dto){

        Ventas ventas = new Ventas();

        ventas.setId(dto.getId());
        ventas.setCantidad(dto.getCantidad());
        ventas.setTotal(dto.getTotal());
        ventas.setMetodoPago(dto.getMetodoPago());
        ventas.setEstado(dto.getEstado());

        ventas.setClienteId(dto.getClienteId());
        ventas.setProductoId(dto.getProductoId());

        return ventas;
    }

    public List<VentasDTO> toListDTO(List<Ventas> lista){

        return lista.stream()
                .map(this::toDTO)
                .toList();
    }
}