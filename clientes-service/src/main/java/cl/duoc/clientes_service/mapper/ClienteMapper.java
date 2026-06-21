package cl.duoc.clientes_service.mapper;

import cl.duoc.clientes_service.dto.ClienteDTO;
import cl.duoc.clientes_service.modelo.Cliente;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ClienteMapper {
        public ClienteDTO toDTO(Cliente cliente){
            if (cliente==null) return null;
            ClienteDTO dto = new ClienteDTO();
            dto.setId(cliente.getId());
            dto.setNombre(cliente.getNombre() + " " + cliente.getApellido());
            dto.setTelefono(cliente.getTelefono());
            dto.setEmail(cliente.getEmail());
            return dto;
        }

        public List<ClienteDTO> toDTOlist(List<Cliente> listado){
            return listado.stream()
                    .map(this::toDTO)
                    .toList();
        }
}
