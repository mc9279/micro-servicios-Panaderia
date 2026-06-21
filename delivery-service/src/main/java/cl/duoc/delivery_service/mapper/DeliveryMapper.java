package cl.duoc.delivery_service.mapper;

import cl.duoc.delivery_service.Clientes.VentaCliente;
import cl.duoc.delivery_service.dto.DeliveryDTO;
import cl.duoc.delivery_service.modelo.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeliveryMapper {
    @Autowired
    private VentaCliente ventaCliente;



    public DeliveryDTO toDTO(Delivery delivery){

        if(delivery==null) return null;

        DeliveryDTO dto = new DeliveryDTO();

        dto.setId(delivery.getId());
        dto.setFechaEntrega(delivery.getFechaEntrega());
        dto.setEstado(delivery.getEstado());
        dto.setVentas(ventaCliente.buscarVentasPorId(delivery.getVentaId()));
        return dto;

    }
    public Delivery toEntity(DeliveryDTO dto){

        Delivery delivery = new Delivery();
        delivery.setId(dto.getId());
        delivery.setFechaEntrega(dto.getFechaEntrega());
        delivery.setEstado(dto.getEstado());
        delivery.setVentaId(dto.getVentaId());
        return delivery;
    }



    public List<DeliveryDTO>
    toListDTO(List<Delivery> lista){

        return lista.stream()
                .map(this::toDTO)
                .toList();
    }

}
