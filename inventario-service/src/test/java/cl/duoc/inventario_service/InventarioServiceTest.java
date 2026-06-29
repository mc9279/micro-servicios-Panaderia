package cl.duoc.inventario_service;
import cl.duoc.inventario_service.dto.InventarioDTO;
import cl.duoc.inventario_service.exception.InventarioNoEncontradoException;
import cl.duoc.inventario_service.mapper.InventarioMapper;
import cl.duoc.inventario_service.modelo.Inventario;
import cl.duoc.inventario_service.repository.InventarioRepository;
import cl.duoc.inventario_service.service.InventarioService;

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
@DisplayName("Pruebas unitarias para InventarioService")

public class InventarioServiceTest {
    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private InventarioMapper inventarioMapper;

    @InjectMocks
    private InventarioService inventarioService;

    private Inventario inventario;
    private InventarioDTO inventarioDTO;

    @BeforeEach
    void setUp() {

        inventario = new Inventario();
        inventario.setId(1L);
        inventario.setStock(25);
        inventario.setProductoId(2L);
        inventario.setPanaderoId(3L);

        inventarioDTO = new InventarioDTO();
        inventarioDTO.setId(1L);
        inventarioDTO.setStock(25);
        inventarioDTO.setProductoId(2L);
        inventarioDTO.setPanaderoId(3L);
    }

    @Test
    @DisplayName("Debe listar todo el inventario")
    void listarTodos_deberiaRetornarListaDTO() {

        when(inventarioRepository.findAll()).thenReturn(List.of(inventario));
        when(inventarioMapper.toListDTO(List.of(inventario))).thenReturn(List.of(inventarioDTO));

        List<InventarioDTO> resultado = inventarioService.findDTOList();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(25, resultado.get(0).getStock());

        verify(inventarioRepository).findAll();
        verify(inventarioMapper).toListDTO(List.of(inventario));
    }

    @Test
    @DisplayName("Debe buscar un inventario por ID")
    void buscarPorId_cuandoExiste() {

        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventario));

        when(inventarioMapper.toDTO(inventario)).thenReturn(inventarioDTO);

        InventarioDTO resultado = inventarioService.findById(1L);

        assertNotNull(resultado);
        assertEquals(25, resultado.getStock());
        assertEquals(2L, resultado.getProductoId());

        verify(inventarioRepository).findById(1L);
        verify(inventarioMapper).toDTO(inventario);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el inventario no existe")
    void buscarPorId_cuandoNoExiste() {

        Long id = 99L;

        when(inventarioRepository.findById(id)).thenReturn(Optional.empty());

        InventarioNoEncontradoException exception = assertThrows(InventarioNoEncontradoException.class, () -> inventarioService.findById(id));

        assertEquals("No existe un inventario con id: 99", exception.getMessage());

        verify(inventarioRepository).findById(id);
        verifyNoInteractions(inventarioMapper);
    }

    @Test
    @DisplayName("Debe guardar un inventario")
    void guardar_deberiaGuardarInventario() {

        when(inventarioMapper.toEntity(inventarioDTO)).thenReturn(inventario);

        when(inventarioRepository.save(inventario)).thenReturn(inventario);

        when(inventarioMapper.toDTO(inventario)).thenReturn(inventarioDTO);

        InventarioDTO resultado = inventarioService.save(inventarioDTO);

        assertNotNull(resultado);
        assertEquals(25, resultado.getStock());

        verify(inventarioMapper).toEntity(inventarioDTO);
        verify(inventarioRepository).save(inventario);
        verify(inventarioMapper).toDTO(inventario);
    }

    @Test
    @DisplayName("Debe actualizar un inventario")
    void actualizar_deberiaActualizarInventario() {

        InventarioDTO nuevo = new InventarioDTO();
        nuevo.setStock(80);
        nuevo.setProductoId(10L);
        nuevo.setPanaderoId(20L);

        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventario));

        when(inventarioRepository.save(any(Inventario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Inventario actualizado = new Inventario();
        actualizado.setId(1L);
        actualizado.setStock(80);
        actualizado.setProductoId(10L);
        actualizado.setPanaderoId(20L);

        InventarioDTO dtoActualizado = new InventarioDTO();
        dtoActualizado.setId(1L);
        dtoActualizado.setStock(80);
        dtoActualizado.setProductoId(10L);
        dtoActualizado.setPanaderoId(20L);

        when(inventarioMapper.toDTO(any(Inventario.class)))
                .thenReturn(dtoActualizado);

        InventarioDTO resultado = inventarioService.update(1L, nuevo);

        assertNotNull(resultado);
        assertEquals(80, resultado.getStock());
        assertEquals(10L, resultado.getProductoId());
        assertEquals(20L, resultado.getPanaderoId());

        verify(inventarioRepository).findById(1L);
        verify(inventarioRepository).save(any(Inventario.class));
        verify(inventarioMapper).toDTO(any(Inventario.class));
    }

    @Test
    @DisplayName("Debe retornar null al actualizar un inventario inexistente")
    void actualizar_cuandoNoExiste() {

        when(inventarioRepository.findById(1L)).thenReturn(Optional.empty());

        InventarioDTO resultado = inventarioService.update(1L, inventarioDTO);

        assertNull(resultado);

        verify(inventarioRepository).findById(1L);
        verify(inventarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un inventario")
    void eliminar_deberiaEliminarInventario() {

        when(inventarioRepository.existsById(1L)).thenReturn(true);

        inventarioService.delete(1L);

        verify(inventarioRepository).existsById(1L);
        verify(inventarioRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar un inventario inexistente")
    void eliminar_cuandoNoExiste() {

        when(inventarioRepository.existsById(99L)).thenReturn(false);
        InventarioNoEncontradoException exception = assertThrows(InventarioNoEncontradoException.class, () -> inventarioService.delete(99L));

        assertEquals("No existe un inventario con id: 99", exception.getMessage());

        verify(inventarioRepository).existsById(99L);
        verify(inventarioRepository, never()).deleteById(anyLong());
    }
}

