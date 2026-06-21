package cl.duoc.clientes_service.service;

import cl.duoc.clientes_service.dto.ClienteDTO;
import cl.duoc.clientes_service.exception.ClienteNoEncontradoException;
import cl.duoc.clientes_service.mapper.ClienteMapper;
import cl.duoc.clientes_service.modelo.Cliente;
import cl.duoc.clientes_service.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;


    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }


    public ClienteDTO findById(Long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(()-> new ClienteNoEncontradoException("No existe un cliente con id: " + id));

        return clienteMapper.toDTO(cliente);
    }

    public Cliente save(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public void delete(Long id){
        clienteRepository.deleteById(id);
    }

    public Cliente update(Long id,Cliente cliente){
        Cliente clienteActualizado = clienteRepository.findById(id).orElse(null);
        if (clienteActualizado == null) return null;
        clienteActualizado.setNombre(cliente.getNombre());
        clienteActualizado.setRut(cliente.getRut());
        clienteActualizado.setEmail(cliente.getEmail());
        clienteActualizado.setTelefono(cliente.getTelefono());
        clienteActualizado.setDireccion(cliente.getDireccion());
        return clienteRepository.save(clienteActualizado);

    }
    public List<ClienteDTO> findDTOList(){
        return clienteMapper.toDTOlist(findAll());
    }



}






