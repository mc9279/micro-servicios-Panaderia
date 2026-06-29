package cl.duoc.panadero_service.service;

import cl.duoc.panadero_service.dto.PanaderoDTO;
import cl.duoc.panadero_service.exception.PanaderoNoEncontradoException;
import cl.duoc.panadero_service.mapper.PanaderoMapper;
import cl.duoc.panadero_service.modelo.Panadero;
import cl.duoc.panadero_service.repository.PanaderoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PanaderoService {
    @Autowired
    private PanaderoRepository panaderoRepository;

    @Autowired
    private PanaderoMapper panaderoMapper;


    public List<Panadero> findAll(){
        return panaderoRepository.findAll();
    }



    public PanaderoDTO findById(Long id){
        Panadero panadero = panaderoRepository.findById(id).orElseThrow(()-> new PanaderoNoEncontradoException("No existe un panadero con id: " + id));

        return panaderoMapper.toDTO(panadero);
    }

    public Panadero save(Panadero panadero){
        return panaderoRepository.save(panadero);
    }

    public void delete(Long id){
        panaderoRepository.deleteById(id);
    }

    public Panadero update(Long id,Panadero panadero){
        Panadero panaderoActulizado = panaderoRepository.findById(id).orElse(null);
        if (panaderoActulizado == null) return null;
        panaderoActulizado.setNombre(panadero.getNombre());
        panaderoActulizado.setEspecialidad(panadero.getEspecialidad());
        panaderoActulizado.setExperiencia(panadero.getExperiencia());

        return panaderoRepository.save(panaderoActulizado);

    }
    public List<PanaderoDTO> findDTOList(){
        return panaderoMapper.toDTOlist(findAll());
    }
}
