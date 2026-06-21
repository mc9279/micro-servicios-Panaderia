package cl.duoc.delivery_service.service;

import cl.duoc.delivery_service.dto.DeliveryDTO;
import cl.duoc.delivery_service.dto.VentasDTO;
import cl.duoc.delivery_service.exception.DeliveryNoencontradoException;
import cl.duoc.delivery_service.mapper.DeliveryMapper;
import cl.duoc.delivery_service.modelo.Delivery;
import cl.duoc.delivery_service.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryMapper deliveryMapper;


    public List<Delivery> findAll(){
        return deliveryRepository.findAll();
    }


    public DeliveryDTO findById(Long id){
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(()-> new DeliveryNoencontradoException("No existe un delivery con id: " + id));

        return deliveryMapper.toDTO(delivery);
    }

    public DeliveryDTO save(DeliveryDTO dto){

        Delivery delivery = deliveryMapper.toEntity(dto);

        Delivery guardado = deliveryRepository.save(delivery);

        return deliveryMapper.toDTO(guardado);
    }


    public void delete(Long id){

        if(!deliveryRepository.existsById(id)){throw new DeliveryNoencontradoException("No existe un Delivery con id: " + id);
        }

        deliveryRepository.deleteById(id);
    }
    public DeliveryDTO update(Long id, DeliveryDTO dto){

        Delivery deliveryActulizado = deliveryRepository.findById(id).orElse(null);

        if(deliveryActulizado == null){return null;
        }

        deliveryActulizado.setFechaEntrega(dto.getFechaEntrega());
        deliveryActulizado.setEstado(dto.getEstado());
        deliveryActulizado.setVentaId(dto.getVentaId());

        Delivery actualizado = deliveryRepository.save(deliveryActulizado);

        return deliveryMapper.toDTO(actualizado);
    }
    public List<DeliveryDTO> findDTOList(){
        return deliveryMapper.toListDTO(findAll());
    }

}
