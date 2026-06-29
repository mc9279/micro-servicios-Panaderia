package cl.duoc.ventas_service;

import cl.duoc.ventas_service.Cliente.ClienteClientes;
import cl.duoc.ventas_service.Cliente.ProductoClientes;
import cl.duoc.ventas_service.dto.ClienteDTO;
import cl.duoc.ventas_service.dto.ProductoDTO;
import cl.duoc.ventas_service.dto.VentasDTO;
import cl.duoc.ventas_service.exception.VentasNoencontradaException;
import cl.duoc.ventas_service.mapper.VentasMapper;
import cl.duoc.ventas_service.modelo.Ventas;
import cl.duoc.ventas_service.repository.VentasRepository;
import cl.duoc.ventas_service.service.VentasService;
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
class VentasServiceTest {

    @Mock
    private VentasRepository ventasRepository;

    @Mock
    private VentasMapper ventasMapper;

    @InjectMocks
    private VentasService ventasService;

    private Ventas ventas;
    private Ventas ventasSinId;
    private VentasDTO ventasDTO;

    @BeforeEach
    public void setUp() {

        ventas = new Ventas(
                1L,
                2,
                50000.0,
                "Tarjeta",
                "Pagada",
                1L,
                3L
        );

        ventasSinId = new Ventas(
                null,
                2,
                50000.0,
                "Tarjeta",
                "Pagada",
                1L,
                3L
        );

        ventasDTO = new VentasDTO();
        ventasDTO.setId(1L);
        ventasDTO.setCantidad(2);
        ventasDTO.setTotal(50000.0);
        ventasDTO.setMetodoPago("Tarjeta");
        ventasDTO.setEstado("Pagada");
        ventasDTO.setClienteId(1L);
        ventasDTO.setProductoId(3L);
    }

    @Test
    @DisplayName("Debe listar todas las ventas")
    public void listarTodas_deberiaRetornarListaVentasDTO() {

        when(ventasRepository.findAll()).thenReturn(List.of(ventas));
        when(ventasMapper.toListDTO(List.of(ventas))).thenReturn(List.of(ventasDTO));

        List<VentasDTO> resultado = ventasService.findDTOList();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(2, resultado.get(0).getCantidad());
        assertEquals(50000.0, resultado.get(0).getTotal());

        verify(ventasRepository).findAll();
        verify(ventasMapper).toListDTO(List.of(ventas));
    }

    @Test
    @DisplayName("Debe guardar una venta")
    public void guardar_deberiaGuardarVenta() {

        when(ventasMapper.toEntity(ventasDTO)).thenReturn(ventasSinId);
        when(ventasRepository.save(ventasSinId)).thenReturn(ventas);
        when(ventasMapper.toDTO(ventas)).thenReturn(ventasDTO);

        VentasDTO resultado = ventasService.save(ventasDTO);

        assertNotNull(resultado);
        assertEquals(2, resultado.getCantidad());
        assertEquals(50000.0, resultado.getTotal());

        verify(ventasMapper).toEntity(ventasDTO);
        verify(ventasRepository).save(ventasSinId);
        verify(ventasMapper).toDTO(ventas);
    }

    @Test
    @DisplayName("Debe buscar una venta por ID cuando existe")
    public void buscarPorId_cuandoExiste() {

        Long id = 1L;

        when(ventasRepository.findById(id)).thenReturn(Optional.of(ventas));
        when(ventasMapper.toDTO(ventas)).thenReturn(ventasDTO);

        VentasDTO resultado = ventasService.findById(id);

        assertNotNull(resultado);
        assertEquals(2, resultado.getCantidad());
        assertEquals("Tarjeta", resultado.getMetodoPago());

        verify(ventasRepository).findById(id);
        verify(ventasMapper).toDTO(ventas);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la venta no existe")
    public void buscarPorId_cuandoNoExiste() {

        Long id = 99L;

        when(ventasRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                VentasNoencontradaException.class,
                ()->ventasService.findById(id)
        );

        verify(ventasRepository).findById(id);
        verifyNoInteractions(ventasMapper);
    }

    @Test
    @DisplayName("Debe actualizar una venta")
    public void actualizar_deberiaActualizarVenta() {

        Long id = 1L;

        when(ventasRepository.findById(id)).thenReturn(Optional.of(ventas));
        when(ventasRepository.save(any(Ventas.class))).thenReturn(ventas);
        when(ventasMapper.toDTO(ventas)).thenReturn(ventasDTO);

        VentasDTO resultado = ventasService.update(id, ventasDTO);

        assertNotNull(resultado);

        verify(ventasRepository).findById(id);
        verify(ventasRepository).save(any(Ventas.class));
        verify(ventasMapper).toDTO(ventas);
    }

    @Test
    @DisplayName("Debe retornar null cuando intenta actualizar una venta inexistente")
    public void actualizar_cuandoNoExiste() {

        Long id = 100L;

        when(ventasRepository.findById(id)).thenReturn(Optional.empty());

        VentasDTO resultado = ventasService.update(id, ventasDTO);

        assertNull(resultado);

        verify(ventasRepository).findById(id);
        verify(ventasRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar una venta")
    public void eliminar_deberiaEliminarVenta() {

        Long id = 1L;

        when(ventasRepository.existsById(id)).thenReturn(true);

        ventasService.delete(id);

        verify(ventasRepository).existsById(id);
        verify(ventasRepository).deleteById(id);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar una venta inexistente")
    public void eliminar_cuandoNoExiste() {

        Long id = 99L;

        when(ventasRepository.existsById(id)).thenReturn(false);

        assertThrows(
                VentasNoencontradaException.class,
                () -> ventasService.delete(id)
        );

        verify(ventasRepository).existsById(id);
        verify(ventasRepository, never()).deleteById(anyLong());
    }}