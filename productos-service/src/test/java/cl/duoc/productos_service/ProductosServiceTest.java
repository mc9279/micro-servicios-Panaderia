package cl.duoc.productos_service;
import cl.duoc.productos_service.dto.ProductosDTO;
import cl.duoc.productos_service.exception.ProductosNoEncontradoException;
import cl.duoc.productos_service.mapper.ProductoMapper;
import cl.duoc.productos_service.modelo.Productos;
import cl.duoc.productos_service.repository.ProductosRepository;
import cl.duoc.productos_service.service.ProductosService;
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
@DisplayName("Pruebas unitarias para ProductosService")
public class ProductosServiceTest {
    @Mock
private ProductosRepository productosRepository;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductosService productosService;

    private Productos producto;
    private Productos productoSinId;
    private ProductosDTO productoDTO;

    @BeforeEach
    public void setUp() {

        producto = new Productos(
                1L,
                "Hallulla",
                "Pan",
                1200.0,
                true,
                "Pan fresco del día"
        );

        productoSinId = new Productos(
                null,
                "Hallulla",
                "Pan",
                1200.0,
                true,
                "Pan fresco del día"
        );

        productoDTO = new ProductosDTO();
        productoDTO.setId(1L);
        productoDTO.setNombre("Hallulla");
        productoDTO.setCategoria("Pan");
        productoDTO.setPrecio(1200.0);
        productoDTO.setDisponible(true);
        productoDTO.setDescripcion("Pan fresco del día");
    }

    @Test
    @DisplayName("Debe listar todos los productos")
    public void listarTodos_deberiaRetornarListaDeProductosDTO() {

        when(productosRepository.findAll()).thenReturn(List.of(producto));
        when(productoMapper.toDTOlist(List.of(producto))).thenReturn(List.of(productoDTO));

        List<ProductosDTO> resultado = productosService.findDTOList();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Hallulla", resultado.get(0).getNombre());

        verify(productosRepository).findAll();
        verify(productoMapper).toDTOlist(List.of(producto));
    }

    @Test
    @DisplayName("Debe guardar un producto")
    public void guardar_deberiaGuardarProducto() {

        when(productosRepository.save(productoSinId)).thenReturn(producto);

        Productos resultado = productosService.save(productoSinId);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Hallulla", resultado.getNombre());

        verify(productosRepository).save(productoSinId);
        verifyNoInteractions(productoMapper);
    }

    @Test
    @DisplayName("Debe buscar un producto por ID")
    public void buscarPorId_cuandoExiste_deberiaRetornarProductoDTO() {

        when(productosRepository.findById(1L)).thenReturn(Optional.of(producto));

        when(productoMapper.toDTO(producto)).thenReturn(productoDTO);

        ProductosDTO resultado = productosService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Hallulla", resultado.getNombre());
        assertEquals("Pan", resultado.getCategoria());

        verify(productosRepository).findById(1L);
        verify(productoMapper).toDTO(producto);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el producto no existe")
    public void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        Long id = 99L;

        when(productosRepository.findById(id)).thenReturn(Optional.empty());

        ProductosNoEncontradoException exception = assertThrows(ProductosNoEncontradoException.class, () -> productosService.findById(id));

        assertEquals("No existe un producto con id: 99", exception.getMessage());

        verify(productosRepository).findById(id);
        verifyNoInteractions(productoMapper);
    }

    @Test
    @DisplayName("Debe actualizar un producto existente")
    public void actualizar_deberiaActualizarProducto() {

        Productos actualizado = new Productos(
                1L,
                "Marraqueta",
                "Pan Especial",
                1500.0,
                false,
                "Pan recién horneado"
        );

        when(productosRepository.findById(1L)).thenReturn(Optional.of(producto));

        when(productosRepository.save(any(Productos.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Productos resultado = productosService.update(1L, actualizado);

        assertNotNull(resultado);
        assertEquals("Marraqueta", resultado.getNombre());
        assertEquals("Pan Especial", resultado.getCategoria());
        assertEquals(1500.0, resultado.getPrecio());
        assertFalse(resultado.getDisponible());
        assertEquals("Pan recién horneado", resultado.getDescripcion());

        verify(productosRepository).findById(1L);
        verify(productosRepository).save(any(Productos.class));
    }

    @Test
    @DisplayName("Debe retornar null al actualizar un producto inexistente")
    public void actualizar_cuandoNoExiste_deberiaRetornarNull() {

        when(productosRepository.findById(1L)).thenReturn(Optional.empty());

        Productos resultado = productosService.update(1L, producto);

        assertNull(resultado);

        verify(productosRepository).findById(1L);
        verify(productosRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un producto por ID")
    public void eliminar_deberiaEliminarProducto() {

        Long id = 1L;

        productosService.delete(id);

        verify(productosRepository).deleteById(id);
    }
}


