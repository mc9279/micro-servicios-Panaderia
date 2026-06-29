package cl.duoc.clientes_service;
import cl.duoc.clientes_service.dto.ClienteDTO;
import cl.duoc.clientes_service.exception.ClienteNoEncontradoException;
import cl.duoc.clientes_service.mapper.ClienteMapper;
import cl.duoc.clientes_service.modelo.Cliente;
import cl.duoc.clientes_service.repository.ClienteRepository;
import cl.duoc.clientes_service.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para ClienteService")

public class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private Cliente clienteSinId;
    private ClienteDTO clienteDTO;

    @BeforeEach
    public void setUp() {

        cliente = new Cliente(
                1L,
                "Juan",
                "Pérez",
                "11111111-1",
                "juan@gmail.com",
                "987654321",
                "Av. Siempre Viva 123"
        );

        clienteSinId = new Cliente(
                null,
                "Juan",
                "Pérez",
                "11111111-1",
                "juan@gmail.com",
                "987654321",
                "Av. Siempre Viva 123"
        );

        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNombre("Juan Pérez");
        clienteDTO.setEmail("juan@gmail.com");
        clienteDTO.setTelefono("987654321");
    }

    @Test
    @DisplayName("Debe listar todos los clientes")
    public void listarTodos_deberiaRetornarListaClientesDTO() {

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        when(clienteMapper.toDTOlist(List.of(cliente))).thenReturn(List.of(clienteDTO));

        List<ClienteDTO> resultado = clienteService.findDTOList();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());

        verify(clienteRepository).findAll();
        verify(clienteMapper).toDTOlist(List.of(cliente));
    }

    @Test
    @DisplayName("Debe guardar un cliente")
    public void guardar_deberiaGuardarCliente() {

        when(clienteRepository.save(clienteSinId)).thenReturn(cliente);

        Cliente resultado = clienteService.save(clienteSinId);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan", resultado.getNombre());

        verify(clienteRepository).save(clienteSinId);
        verifyNoInteractions(clienteMapper);
    }

    @Test
    @DisplayName("Debe buscar un cliente por ID")
    public void buscarPorId_cuandoExiste_deberiaRetornarClienteDTO() {

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO resultado = clienteService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombre());
        assertEquals("juan@gmail.com", resultado.getEmail());

        verify(clienteRepository).findById(1L);
        verify(clienteMapper).toDTO(cliente);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el cliente no existe")
    public void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        Long id = 99L;

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        ClienteNoEncontradoException exception = assertThrows(ClienteNoEncontradoException.class, () -> clienteService.findById(id));

        assertEquals("No existe un cliente con id: 99", exception.getMessage());

        verify(clienteRepository).findById(id);
        verifyNoInteractions(clienteMapper);
    }

    @Test
    @DisplayName("Debe actualizar un cliente existente")
    public void actualizar_deberiaActualizarCliente() {

        Cliente actualizado = new Cliente(
                1L,
                "Pedro",
                "Soto",
                "22222222-2",
                "pedro@gmail.com",
                "999999999",
                "Nueva Dirección 456"
        );

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = clienteService.update(1L, actualizado);

        assertNotNull(resultado);
        assertEquals("Pedro", resultado.getNombre());
        assertEquals("Soto", resultado.getApellido());
        assertEquals("22222222-2", resultado.getRut());
        assertEquals("pedro@gmail.com", resultado.getEmail());
        assertEquals("999999999", resultado.getTelefono());
        assertEquals("Nueva Dirección 456", resultado.getDireccion());

        verify(clienteRepository).findById(1L);
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Debe retornar null al actualizar un cliente inexistente")
    public void actualizar_cuandoNoExiste_deberiaRetornarNull() {

        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        Cliente resultado = clienteService.update(1L, cliente);

        assertNull(resultado);

        verify(clienteRepository).findById(1L);
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un cliente por ID")
    public void eliminar_deberiaEliminarCliente() {

        Long id = 1L;

        clienteService.delete(id);

        verify(clienteRepository).deleteById(id);
    }
}
