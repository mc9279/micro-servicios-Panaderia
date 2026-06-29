package cl.duoc.inventario_service.controller;

import cl.duoc.inventario_service.dto.InventarioDTO;
import cl.duoc.inventario_service.exception.ErrorResponse;
import cl.duoc.inventario_service.modelo.Inventario;
import cl.duoc.inventario_service.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido correctamente",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InventarioDTO.class)))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
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
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventario encontrado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventarioDTO.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inventario no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<InventarioDTO> buscarporId(
            @Parameter(description = "ID del panadero a actualizar", example = "1")
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
            @ApiResponse(
                    responseCode = "201",
                    description = "Inventario registrado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventarioDTO.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
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
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventario actualizado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventarioDTO.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inventario no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<InventarioDTO>actualizado(
            @Parameter(description = "ID del panadero a actualizar", example = "1")
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
            @ApiResponse(
                    responseCode = "204",
                    description = "Inventario eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del panadero a actualizar", example = "1")
            @PathVariable Long id){
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
