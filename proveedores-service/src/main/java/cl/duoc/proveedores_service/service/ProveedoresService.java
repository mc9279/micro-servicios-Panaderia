package cl.duoc.proveedores_service.service;

import cl.duoc.proveedores_service.dto.ProveedoresDTO;
import cl.duoc.proveedores_service.exception.ProveedoresNoEncontradoException;
import cl.duoc.proveedores_service.mapper.ProveedoresMapper;
import cl.duoc.proveedores_service.modelo.Proveedores;
import cl.duoc.proveedores_service.repository.ProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProveedoresService {
    @Autowired
    private ProveedoresRepository proveedoresRepository;

    @Autowired
    private ProveedoresMapper proveedoresMapper;


    public List<Proveedores> findAll(){
        return proveedoresRepository.findAll();
    }


    public ProveedoresDTO findById(Long id){

        Proveedores proveedorese = proveedoresRepository.findById(id).orElseThrow(() -> new ProveedoresNoEncontradoException("No existe un producto con id: " + id));

        return proveedoresMapper.toDTO(proveedorese);
    }



    public Proveedores save(Proveedores proveedores){
        return proveedoresRepository.save(proveedores);
    }

    public void delete(Long id){
        proveedoresRepository.deleteById(id);
    }

    public Proveedores update(Long id,Proveedores proveedores){
        Proveedores proveedoresActulizado = proveedoresRepository.findById(id).orElse(null);
        if (proveedoresActulizado == null) return null;
        proveedoresActulizado.setNombreEmpresa(proveedores.getNombreEmpresa());
        proveedoresActulizado.setRut(proveedores.getRut());
        proveedoresActulizado.setTelefono(proveedores.getTelefono());
        proveedoresActulizado.setEmail(proveedores.getEmail());
        proveedoresActulizado.setDireccion(proveedores.getDireccion());
        return proveedoresRepository.save(proveedoresActulizado);


    }
    public List<ProveedoresDTO> findDTOList(){return proveedoresMapper.toDTOlist(findAll());}
}
