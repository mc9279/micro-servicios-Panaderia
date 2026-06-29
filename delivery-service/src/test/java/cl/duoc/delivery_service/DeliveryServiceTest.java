package cl.duoc.delivery_service;

import cl.duoc.delivery_service.dto.DeliveryDTO;
import cl.duoc.delivery_service.dto.VentasDTO;
import cl.duoc.delivery_service.exception.DeliveryNoencontradoException;
import cl.duoc.delivery_service.mapper.DeliveryMapper;
import cl.duoc.delivery_service.modelo.Delivery;
import cl.duoc.delivery_service.repository.DeliveryRepository;
import cl.duoc.delivery_service.service.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para DeliveryService")

public class DeliveryServiceTest {
    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliveryMapper deliveryMapper;

    @InjectMocks
    private DeliveryService deliveryService;

    private Delivery delivery;
    private Delivery deliverySinId;
    private DeliveryDTO deliveryDTO;
    private VentasDTO ventasDTO;

    @BeforeEach
    public void setUp() {

        delivery = new Delivery(
                1L,
                "Av. Siempre Viva 123",
                LocalDateTime.of(2026, 6, 1, 15, 30),
                "Pendiente",
                1L
        );

        deliverySinId = new Delivery(
                null,
                "Av. Siempre Viva 123",
                LocalDateTime.of(2026, 6, 1, 15, 30),
                "Pendiente",
                1L
        );

        ventasDTO = new VentasDTO();
        ventasDTO.setId(1L);
        ventasDTO.setCantidad(3);
        ventasDTO.setMetodoPago("Efectivo");

        deliveryDTO = new DeliveryDTO();
        deliveryDTO.setId(1L);
        deliveryDTO.setFechaEntrega(LocalDateTime.of(2026, 6, 1, 15, 30));
        deliveryDTO.setEstado("Pendiente");
        deliveryDTO.setVentaId(1L);
        deliveryDTO.setVentas(ventasDTO);
    }

    @Test
    @DisplayName("Debe listar todos los delivery")
    public void listarTodos_deberiaRetornarListaDeliveryDTO() {

        when(deliveryRepository.findAll()).thenReturn(List.of(delivery));
        when(deliveryMapper.toListDTO(List.of(delivery))).thenReturn(List.of(deliveryDTO));

        List<DeliveryDTO> resultado = deliveryService.findDTOList();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pendiente", resultado.get(0).getEstado());

        verify(deliveryRepository).findAll();
        verify(deliveryMapper).toListDTO(List.of(delivery));
    }

    @Test
    @DisplayName("Debe guardar un delivery")
    public void guardar_deberiaGuardarDelivery() {

        when(deliveryMapper.toEntity(deliveryDTO)).thenReturn(deliverySinId);
        when(deliveryRepository.save(deliverySinId)).thenReturn(delivery);
        when(deliveryMapper.toDTO(delivery)).thenReturn(deliveryDTO);

        DeliveryDTO resultado = deliveryService.save(deliveryDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Pendiente", resultado.getEstado());

        verify(deliveryMapper).toEntity(deliveryDTO);
        verify(deliveryRepository).save(deliverySinId);
        verify(deliveryMapper).toDTO(delivery);
    }

    @Test
    @DisplayName("Debe buscar un delivery por ID")
    public void buscarPorId_cuandoExiste_deberiaRetornarDeliveryDTO() {

        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));
        when(deliveryMapper.toDTO(delivery)).thenReturn(deliveryDTO);

        DeliveryDTO resultado = deliveryService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Pendiente", resultado.getEstado());

        verify(deliveryRepository).findById(1L);
        verify(deliveryMapper).toDTO(delivery);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el delivery no existe")
    public void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        Long id = 99L;

        when(deliveryRepository.findById(id)).thenReturn(Optional.empty());

        DeliveryNoencontradoException exception = assertThrows(DeliveryNoencontradoException.class, () -> deliveryService.findById(id));

        assertEquals("No existe un delivery con id: 99", exception.getMessage());

        verify(deliveryRepository).findById(id);
        verifyNoInteractions(deliveryMapper);
    }

    @Test
    @DisplayName("Debe actualizar un delivery existente")
    public void actualizar_deberiaActualizarDelivery() {

        DeliveryDTO actualizado = new DeliveryDTO();
        actualizado.setId(1L);
        actualizado.setFechaEntrega(LocalDateTime.of(2026, 7, 1, 10, 0));
        actualizado.setEstado("Entregado");
        actualizado.setVentaId(2L);

        Delivery deliveryActualizado = new Delivery(
                1L,
                "Av. Siempre Viva 123",
                actualizado.getFechaEntrega(),
                actualizado.getEstado(),
                actualizado.getVentaId()
        );

        DeliveryDTO dtoActualizado = new DeliveryDTO();
        dtoActualizado.setId(1L);
        dtoActualizado.setFechaEntrega(actualizado.getFechaEntrega());
        dtoActualizado.setEstado("Entregado");
        dtoActualizado.setVentaId(2L);

        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(deliveryActualizado);
        when(deliveryMapper.toDTO(deliveryActualizado)).thenReturn(dtoActualizado);

        DeliveryDTO resultado = deliveryService.update(1L, actualizado);

        assertNotNull(resultado);
        assertEquals("Entregado", resultado.getEstado());
        assertEquals(2L, resultado.getVentaId());

        verify(deliveryRepository).findById(1L);
        verify(deliveryRepository).save(any(Delivery.class));
        verify(deliveryMapper).toDTO(deliveryActualizado);
    }

    @Test
    @DisplayName("Debe retornar null al actualizar un delivery inexistente")
    public void actualizar_cuandoNoExiste_deberiaRetornarNull() {

        when(deliveryRepository.findById(1L)).thenReturn(Optional.empty());

        DeliveryDTO resultado = deliveryService.update(1L, deliveryDTO);

        assertNull(resultado);

        verify(deliveryRepository).findById(1L);
        verify(deliveryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un delivery existente")
    public void eliminar_deberiaEliminarDelivery() {

        when(deliveryRepository.existsById(1L)).thenReturn(true);

        deliveryService.delete(1L);

        verify(deliveryRepository).existsById(1L);
        verify(deliveryRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar un delivery inexistente")
    public void eliminar_cuandoNoExiste_deberiaLanzarExcepcion() {

        when(deliveryRepository.existsById(99L)).thenReturn(false);

        DeliveryNoencontradoException exception = assertThrows(DeliveryNoencontradoException.class, () -> deliveryService.delete(99L));

        assertEquals("No existe un Delivery con id: 99", exception.getMessage());

        verify(deliveryRepository).existsById(99L);
        verify(deliveryRepository, never()).deleteById(anyLong());
    }

}
