package cl.duoc.proveedores_service;

import cl.duoc.proveedores_service.dto.ProveedoresDTO;
import cl.duoc.proveedores_service.exception.ProveedoresNoEncontradoException;
import cl.duoc.proveedores_service.mapper.ProveedoresMapper;
import cl.duoc.proveedores_service.modelo.Proveedores;
import cl.duoc.proveedores_service.repository.ProveedoresRepository;
import cl.duoc.proveedores_service.service.ProveedoresService;
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
@DisplayName("Pruebas unitarias para ProveedoresService")
public class ProveedoresServiceTest {
    @Mock
    private ProveedoresRepository proveedoresRepository;

    @Mock
    private ProveedoresMapper proveedoresMapper;

    @InjectMocks
    private ProveedoresService proveedoresService;

    private Proveedores proveedor;
    private Proveedores proveedorSinId;
    private ProveedoresDTO proveedorDTO;

    @BeforeEach
    public void setUp() {

        proveedor = new Proveedores(
                1L,
                "Panificadora Los Andes",
                "76.123.456-7",
                "987654321",
                "contacto@losandes.cl",
                "Av. Principal 123"
        );

        proveedorSinId = new Proveedores(
                null,
                "Panificadora Los Andes",
                "76.123.456-7",
                "987654321",
                "contacto@losandes.cl",
                "Av. Principal 123"
        );

        proveedorDTO = new ProveedoresDTO();
        proveedorDTO.setId(1L);
        proveedorDTO.setNombreEmpresa("Panificadora Los Andes");
        proveedorDTO.setTelefono("987654321");
        proveedorDTO.setEmail("contacto@losandes.cl");
    }

    @Test
    @DisplayName("Debe listar todos los proveedores")
    public void listarTodos_deberiaRetornarListaDeProveedoresDTO() {

        when(proveedoresRepository.findAll()).thenReturn(List.of(proveedor));
        when(proveedoresMapper.toDTOlist(List.of(proveedor)))
                .thenReturn(List.of(proveedorDTO));

        List<ProveedoresDTO> resultado = proveedoresService.findDTOList();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Panificadora Los Andes", resultado.get(0).getNombreEmpresa());

        verify(proveedoresRepository).findAll();
        verify(proveedoresMapper).toDTOlist(List.of(proveedor));
    }

    @Test
    @DisplayName("Debe guardar un proveedor")
    public void guardar_deberiaGuardarProveedor() {

        when(proveedoresRepository.save(proveedorSinId)).thenReturn(proveedor);

        Proveedores resultado = proveedoresService.save(proveedorSinId);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Panificadora Los Andes", resultado.getNombreEmpresa());

        verify(proveedoresRepository).save(proveedorSinId);

        verifyNoInteractions(proveedoresMapper);
    }

    @Test
    @DisplayName("Debe buscar un proveedor por ID")
    public void buscarPorId_cuandoExiste_deberiaRetornarProveedorDTO() {

        when(proveedoresRepository.findById(1L)).thenReturn(Optional.of(proveedor));

        when(proveedoresMapper.toDTO(proveedor)).thenReturn(proveedorDTO);

        ProveedoresDTO resultado = proveedoresService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Panificadora Los Andes", resultado.getNombreEmpresa());

        verify(proveedoresRepository).findById(1L);
        verify(proveedoresMapper).toDTO(proveedor);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el proveedor no existe")
    public void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        Long id = 99L;

        when(proveedoresRepository.findById(id)).thenReturn(Optional.empty());

        ProveedoresNoEncontradoException exception = assertThrows(ProveedoresNoEncontradoException.class, () -> proveedoresService.findById(id));

        assertEquals("No existe un producto con id: 99", exception.getMessage());

        verify(proveedoresRepository).findById(id);

        verifyNoInteractions(proveedoresMapper);
    }

    @Test
    @DisplayName("Debe actualizar un proveedor existente")
    public void actualizar_deberiaActualizarProveedor() {

        Proveedores actualizado = new Proveedores(
                1L,
                "Panadería Central",
                "11.111.111-1",
                "999999999",
                "nuevo@correo.cl",
                "Nueva Dirección 456"
        );

        when(proveedoresRepository.findById(1L)).thenReturn(Optional.of(proveedor));

        when(proveedoresRepository.save(any(Proveedores.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Proveedores resultado = proveedoresService.update(1L, actualizado);

        assertNotNull(resultado);
        assertEquals("Panadería Central", resultado.getNombreEmpresa());
        assertEquals("11.111.111-1", resultado.getRut());
        assertEquals("999999999", resultado.getTelefono());
        assertEquals("nuevo@correo.cl", resultado.getEmail());

        verify(proveedoresRepository).findById(1L);
        verify(proveedoresRepository).save(any(Proveedores.class));
    }

    @Test
    @DisplayName("Debe retornar null al actualizar un proveedor inexistente")
    public void actualizar_cuandoNoExiste_deberiaRetornarNull() {

        when(proveedoresRepository.findById(1L)).thenReturn(Optional.empty());

        Proveedores resultado = proveedoresService.update(1L, proveedor);

        assertNull(resultado);

        verify(proveedoresRepository).findById(1L);
        verify(proveedoresRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un proveedor por ID")
    public void eliminar_deberiaEliminarProveedor() {

        Long id = 1L;

        proveedoresService.delete(id);

        verify(proveedoresRepository).deleteById(id);
    }

}

