package cl.duoc.productos_service.controller;

import cl.duoc.productos_service.dto.ProductosDTO;
import cl.duoc.productos_service.modelo.Productos;
import cl.duoc.productos_service.service.ProductosService;
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
@RequestMapping("api/v1/productos")
@Tag(
        name = "Productos",
        description = "API encargada de la gestión de productos disponibles en el sistema."
)
public class ProductoController {
    @Autowired
    private ProductosService productosService;

    @GetMapping
    @Operation(
            summary = "Listar Productos",
            description = "Obtiene el listado completo de productos registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(productosService.findDTOList());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar producto por Id",
            description = "Obtiene la información de un producto específico utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto encontrado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductosDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Long id){
        ProductosDTO productos = productosService.findById(id);

        if(productos == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(productos);
    }

    @PostMapping
    @Operation(
            summary = "Registrar producto",
            description = "Permite registrar un nuevo producto en la base de datos."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Producto registrado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Productos.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> registrar(@Valid @RequestBody Productos productos){
        Productos productosNuevos = productosService.save(productos);
        return new ResponseEntity<>(productosNuevos, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar producto",
            description = "Actualiza los datos de un producto existente mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto actualizado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Productos.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID del producto a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Productos productos){

        Productos productosActualizados =
                productosService.update(id,productos);

        if(productosActualizados == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(productosActualizados);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar producto",
            description = "Elimina un producto registrado en el sistema utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> borrar(
            @Parameter(description = "ID del producto a eliminar", example = "1")
            @PathVariable Long id){
        productosService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
