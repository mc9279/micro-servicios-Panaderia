package cl.duoc.ventas_service.controller;

import cl.duoc.ventas_service.dto.VentasDTO;
import cl.duoc.ventas_service.exception.ErrorResponse;
import cl.duoc.ventas_service.service.VentasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("api/v1/ventas")
@Tag(
        name = "Ventas",
        description = "API encargada de la gestión de ventas realizadas en el sistema."
)
public class VentasController {
    @Autowired
    private VentasService ventasService;

    @GetMapping
    @Operation(
            summary = "Listar ventas",
            description = "Obtiene el listado completo de ventas registradas en el sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de ventas obtenido correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VentasDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<VentasDTO>> listar() {

        List<VentasDTO> ventas = ventasService.findDTOList();

        return ResponseEntity.ok(ventas);
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar venta por ID",
            description = "Obtiene la información de una venta específica utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Venta encontrada correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VentasDTO.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Venta no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<VentasDTO> buscarporId(
            @Parameter(description = "ID de la venta", example = "1")
            @PathVariable Long id){
        VentasDTO ventas = ventasService.findById(id);
        if(ventas==null){return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ventas);
    }
    @PostMapping
    @Operation(
            summary = "Registrar venta",
            description = "Permite registrar una nueva venta asociada a un cliente y sus productos."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Venta registrada correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VentasDTO.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<VentasDTO> crear(
            @Valid @RequestBody VentasDTO dto){
        VentasDTO nuevo = ventasService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar venta",
            description = "Actualiza los datos de una venta existente mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Venta actualizada correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VentasDTO.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Venta no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<VentasDTO>actualizado(
            @Parameter(description = "ID de la venta a actualizar", example = "1")
            @PathVariable Long id,
            @RequestBody VentasDTO dto){
        VentasDTO actualizado = ventasService.update(id,dto);
        if(actualizado== null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar venta",
            description = "Elimina una venta registrada en el sistema utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Venta eliminada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Venta no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la venta a eliminar", example = "1")
            @PathVariable Long id){
        ventasService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
