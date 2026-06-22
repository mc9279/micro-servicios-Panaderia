package cl.duoc.proveedores_service.controller;

import cl.duoc.proveedores_service.dto.ProveedoresDTO;
import cl.duoc.proveedores_service.modelo.Proveedores;
import cl.duoc.proveedores_service.service.ProveedoresService;
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

@RestController
@RequestMapping("api/v1/proveedores")
@Tag(
        name = "Proveedores",
        description = "API encargada de la gestión de proveedores registrados en el sistema."
)
public class ProveedorController {
    @Autowired
    private ProveedoresService proveedoresService;

    @GetMapping
    @Operation(
            summary = "Listar Proveedores",
            description = "Obtiene el listado completo de proveedores registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(proveedoresService.findDTOList());
    }

    @GetMapping("/{id}")

    @Operation(
            summary = "Buscar proveedor por ID",
            description = "Obtiene la información de un proveedor específico utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Proveedor encontrado correctamente", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProveedoresDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID del proveedor", example = "1")
            @PathVariable Long id){
        ProveedoresDTO productos = proveedoresService.findById(id);

        if(productos == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(productos);
    }

    @PostMapping
    @Operation(
            summary = "Registrar proveedor",
            description = "Permite registrar un nuevo proveedor en la base de datos."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Proveedor registrado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proveedores.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> registrar(@Valid @RequestBody Proveedores proveedores){
        Proveedores proveedoresNuevo = proveedoresService.save(proveedores);
        return new ResponseEntity<>(proveedoresNuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar proveedor",
            description = "Actualiza los datos de un proveedor existente mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Proveedor actualizado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proveedores.class))),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Parameter(description = "ID del proveedor", example = "1")
            @Valid @RequestBody Proveedores proveedores){

        Proveedores proveedoresActulizado =
                proveedoresService.update(id,proveedores);

        if(proveedoresActulizado == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(proveedoresActulizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar proveedor",
            description = "Elimina un proveedor registrado en el sistema utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Proveedor eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> borrar(@Parameter(description = "ID del proveedor a eliminar", example = "1") @PathVariable Long id
    ){
        proveedoresService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
