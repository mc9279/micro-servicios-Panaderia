package cl.duoc.panadero_service;
import cl.duoc.panadero_service.dto.PanaderoDTO;
import cl.duoc.panadero_service.exception.PanaderoNoEncontradoException;
import cl.duoc.panadero_service.mapper.PanaderoMapper;
import cl.duoc.panadero_service.modelo.Panadero;
import cl.duoc.panadero_service.repository.PanaderoRepository;
import cl.duoc.panadero_service.service.PanaderoService;
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
@DisplayName("Pruebas unitarias para PanaderoService")

public class PanaderoServiceTest {
    @Mock
    private PanaderoRepository panaderoRepository;

    @Mock
    private PanaderoMapper panaderoMapper;

    @InjectMocks
    private PanaderoService panaderoService;

    private Panadero panadero;
    private Panadero panaderoSinId;
    private PanaderoDTO panaderoDTO;

    @BeforeEach
    public void setUp() {

        panadero = new Panadero(
                1L,
                "Juan Pérez",
                "Masas",
                "10 años"
        );

        panaderoSinId = new Panadero(
                null,
                "Juan Pérez",
                "Masas",
                "10 años"
        );

        panaderoDTO = new PanaderoDTO();
        panaderoDTO.setId(1L);
        panaderoDTO.setNombre("Juan Pérez");
        panaderoDTO.setEspecialidad("Masas");
    }

    @Test
    @DisplayName("Debe listar todos los panaderos")
    public void listarTodos_deberiaRetornarListaDePanaderosDTO() {

        when(panaderoRepository.findAll()).thenReturn(List.of(panadero));
        when(panaderoMapper.toDTOlist(List.of(panadero))).thenReturn(List.of(panaderoDTO));

        List<PanaderoDTO> resultado = panaderoService.findDTOList();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());

        verify(panaderoRepository).findAll();
        verify(panaderoMapper).toDTOlist(List.of(panadero));   }

    @Test
    @DisplayName("Debe guardar un panadero")
    public void guardar_deberiaGuardarPanadero() {
        when(panaderoRepository.save(panaderoSinId)).thenReturn(panadero);

        Panadero resultado = panaderoService.save(panaderoSinId);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombre());

        verify(panaderoRepository).save(panaderoSinId);
        verifyNoInteractions(panaderoMapper);
    }

    @Test
    @DisplayName("Debe buscar un panadero por ID")
    public void buscarPorId_cuandoExiste_deberiaRetornarPanaderoDTO() {

        when(panaderoRepository.findById(1L)).thenReturn(Optional.of(panadero));

        when(panaderoMapper.toDTO(panadero)).thenReturn(panaderoDTO);

        PanaderoDTO resultado = panaderoService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombre());
        assertEquals("Masas", resultado.getEspecialidad());

        verify(panaderoRepository).findById(1L);
        verify(panaderoMapper).toDTO(panadero);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el panadero no existe")
    public void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        Long id = 99L;

        when(panaderoRepository.findById(id))
                .thenReturn(Optional.empty());

        PanaderoNoEncontradoException exception = assertThrows(PanaderoNoEncontradoException.class, () -> panaderoService.findById(id));

        assertEquals("No existe un panadero con id: 99", exception.getMessage());

        verify(panaderoRepository).findById(id);
        verifyNoInteractions(panaderoMapper);
    }

    @Test
    @DisplayName("Debe actualizar un panadero existente")
    public void actualizar_deberiaActualizarPanadero() {

        Panadero actualizado = new Panadero(
                1L,
                "Pedro Soto",
                "Pastelería",
                "15 años"
        );

        when(panaderoRepository.findById(1L)).thenReturn(Optional.of(panadero));

        when(panaderoRepository.save(any(Panadero.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Panadero resultado = panaderoService.update(1L, actualizado);

        assertNotNull(resultado);
        assertEquals("Pedro Soto", resultado.getNombre());
        assertEquals("Pastelería", resultado.getEspecialidad());
        assertEquals("15 años", resultado.getExperiencia());

        verify(panaderoRepository).findById(1L);
        verify(panaderoRepository).save(any(Panadero.class));
    }

    @Test
    @DisplayName("Debe retornar null al actualizar un panadero inexistente")
    public void actualizar_cuandoNoExiste_deberiaRetornarNull() {

        when(panaderoRepository.findById(1L)).thenReturn(Optional.empty());

        Panadero resultado = panaderoService.update(1L, panadero);

        assertNull(resultado);

        verify(panaderoRepository).findById(1L);
        verify(panaderoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un panadero por ID")
    public void eliminar_deberiaEliminarPanadero() {

        Long id = 1L;

        panaderoService.delete(id);

        verify(panaderoRepository).deleteById(id);
    }
}

