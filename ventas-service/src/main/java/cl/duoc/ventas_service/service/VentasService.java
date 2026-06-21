package cl.duoc.ventas_service.service;

import cl.duoc.ventas_service.dto.VentasDTO;
import cl.duoc.ventas_service.exception.VentasNoencontradaException;
import cl.duoc.ventas_service.mapper.VentasMapper;
import cl.duoc.ventas_service.modelo.Ventas;
import cl.duoc.ventas_service.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentasService {
    @Autowired
    private VentasRepository ventasRepository;

    @Autowired
    private VentasMapper ventasMapper;


    public List<Ventas> findAll(){
        return ventasRepository.findAll();
    }


    public VentasDTO findById(Long id){
        Ventas ventas = ventasRepository.findById(id).orElseThrow(()-> new VentasNoencontradaException("No existe un venta con id: " + id));

        return ventasMapper.toDTO(ventas);
    }

    public VentasDTO save(VentasDTO dto){

        Ventas ventas = ventasMapper.toEntity(dto);

        Ventas guardado = ventasRepository.save(ventas);

        return ventasMapper.toDTO(guardado);
    }


    public void delete(Long id){

        if(!ventasRepository.existsById(id)){throw new VentasNoencontradaException("No existe un inventario con id: " + id);
        }

        ventasRepository.deleteById(id);
    }
    public VentasDTO update(Long id, VentasDTO dto){

        Ventas ventasActual = ventasRepository.findById(id).orElse(null);

        if(ventasActual == null){return null;
        }

        ventasActual.setCantidad(dto.getCantidad());
        ventasActual.setMetodoPago(dto.getMetodoPago());
        ventasActual.setTotal(dto.getTotal());
        ventasActual.setEstado(dto.getEstado());
        ventasActual.setProductoId(dto.getProductoId());
        ventasActual.setClienteId(dto.getClienteId());

        Ventas actualizado = ventasRepository.save(ventasActual);

        return ventasMapper.toDTO(actualizado);
    }
    public List<VentasDTO> findDTOList(){
        return ventasMapper.toListDTO(findAll());
    }


}
