package cl.duoc.panadero_service.mapper;

import cl.duoc.panadero_service.dto.PanaderoDTO;
import cl.duoc.panadero_service.modelo.Panadero;

import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PanaderoMapper {
    public PanaderoDTO toDTO(Panadero panadero){
        if (panadero==null) return null;
        PanaderoDTO dto = new PanaderoDTO();
        dto.setId(panadero.getId());
        dto.setNombre(panadero.getNombre());
        dto.setEspecialidad(panadero.getEspecialidad());
        return dto;
    }

    public List<PanaderoDTO> toDTOlist(List<Panadero> listado){
        return listado.stream()
                .map(this::toDTO)
                .toList();
    }
}
