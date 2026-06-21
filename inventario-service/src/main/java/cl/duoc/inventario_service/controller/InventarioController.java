package cl.duoc.inventario_service.controller;

import cl.duoc.inventario_service.dto.InventarioDTO;
import cl.duoc.inventario_service.modelo.Inventario;
import cl.duoc.inventario_service.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventario")
@Tag(
        name = "Inventario",
        description = "API encargada de la gestión del inventario de productos disponibles en el sistema."
)
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(
            summary = "Listar inventario",
            description = "Obtiene el listado completo de registros de inventario."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventario obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<InventarioDTO>> listar() {

        List<InventarioDTO> inventarios =
                inventarioService.findDTOList();

        return ResponseEntity.ok(inventarios);
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar inventario por ID",
            description = "Obtiene la información de un registro de inventario utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<InventarioDTO> buscarporId(
            @PathVariable Long id){
        InventarioDTO inventario = inventarioService.findById(id);
        if(inventario==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inventario);
    }
    @PostMapping
    @Operation(
            summary = "Registrar inventario",
            description = "Permite registrar un nuevo elemento en el inventario."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inventario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<InventarioDTO> crear(
            @Valid @RequestBody InventarioDTO dto){
        InventarioDTO nuevo =
                inventarioService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar inventario",
            description = "Actualiza la información de un registro de inventario existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<InventarioDTO>actualizado(
            @PathVariable Long id,
            @RequestBody InventarioDTO dto){
        InventarioDTO actualizado = inventarioService.update(id,dto);
        if(actualizado== null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar inventario",
            description = "Elimina un registro de inventario utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inventario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id){
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
