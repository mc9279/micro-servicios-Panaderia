package cl.duoc.panadero_service.controller;

import cl.duoc.panadero_service.dto.PanaderoDTO;
import cl.duoc.panadero_service.exception.ErrorResponse;
import cl.duoc.panadero_service.exception.PanaderoNoEncontradoException;
import cl.duoc.panadero_service.modelo.Panadero;
import cl.duoc.panadero_service.service.PanaderoService;
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

@RestController
@RequestMapping("api/v1/panadero")
@Tag(
        name = "Panadero",
        description = "API encargada de la gestión de panaderos y la producción asociada en el sistema."
)
public class PanaderoController {
    @Autowired
    private PanaderoService panaderoService;


    @GetMapping
    @Operation
            (
            summary = "Listar panaderos",
            description = "Obtiene el listado completo de panaderos registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido correctamente",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PanaderoDTO.class)))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(panaderoService.findDTOList());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar panadero por ID",
            description = "Obtiene la información de un panadero específico utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Panadero encontrado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PanaderoDTO.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Panadero no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID del panadero", example = "1")
            @PathVariable Long id){
        PanaderoDTO inventario = panaderoService.findById(id);

        if(inventario == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(inventario);
    }

    @PostMapping
    @Operation(
            summary = "Registrar panadero",
            description = "Permite registrar un nuevo panadero en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Panadero registrado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Panadero.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> registrar(@Valid @RequestBody Panadero panadero){
        Panadero panaderoNuevo = panaderoService.save(panadero);
        return new ResponseEntity<>(panaderoNuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar panadero",
            description = "Actualiza los datos de un panadero existente mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Panadero actualizado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Panadero.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Panadero no encontrado",
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
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID del panadero a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Panadero panadero){

        Panadero panaderoActulizado = panaderoService.update(id,panadero);

        if(panaderoActulizado == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(panaderoActulizado);
    }

    @DeleteMapping("/{id}")
    @Operation
            (

            summary = "Eliminar panadero",
            description = "Elimina un panadero registrado en el sistema utilizando su identificador."
    )
    @ApiResponses({

            @ApiResponse(
                    responseCode = "204",
                    description = "Panadero eliminado correctamente"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Panadero no encontrado",
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
    public ResponseEntity<?> borrar(
            @Parameter(description = "ID del panadero a eliminar", example = "1")
            @PathVariable Long id){
        panaderoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
