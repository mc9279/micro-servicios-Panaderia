package cl.duoc.inventario_service.service;

import cl.duoc.inventario_service.dto.InventarioDTO;
import cl.duoc.inventario_service.exception.InventarioNoEncontradoException;
import cl.duoc.inventario_service.mapper.InventarioMapper;
import cl.duoc.inventario_service.modelo.Inventario;
import cl.duoc.inventario_service.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private InventarioMapper inventarioMapper;


    public List<Inventario> findAll(){
        return inventarioRepository.findAll();
    }


    public InventarioDTO findById(Long id){
        Inventario inventario = inventarioRepository.findById(id).orElseThrow(()-> new InventarioNoEncontradoException("No existe un inventario con id: " + id));

        return inventarioMapper.toDTO(inventario);
    }

    public InventarioDTO save(InventarioDTO dto){

        Inventario inventario = inventarioMapper.toEntity(dto);

        Inventario guardado = inventarioRepository.save(inventario);

        return inventarioMapper.toDTO(guardado);
    }


    public void delete(Long id){

        if(!inventarioRepository.existsById(id)){throw new InventarioNoEncontradoException("No existe un inventario con id: " + id);
        }

        inventarioRepository.deleteById(id);
    }
    public InventarioDTO update(Long id, InventarioDTO dto){

        Inventario inventarioActual = inventarioRepository.findById(id).orElse(null);

        if(inventarioActual == null){return null;
        }

        inventarioActual.setStock(dto.getStock());
        inventarioActual.setProductoId(dto.getProductoId());
        inventarioActual.setPanaderoId(dto.getPanaderoId());

        Inventario actualizado = inventarioRepository.save(inventarioActual);

        return inventarioMapper.toDTO(actualizado);
    }
    public List<InventarioDTO> findDTOList(){
        return inventarioMapper.toListDTO(findAll());
    }


}
