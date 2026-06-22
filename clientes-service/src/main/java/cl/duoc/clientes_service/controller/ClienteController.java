package cl.duoc.clientes_service.controller;

import cl.duoc.clientes_service.dto.ClienteDTO;
import cl.duoc.clientes_service.modelo.Cliente;
import cl.duoc.clientes_service.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/clientes")
@Tag(
        name = "Clientes",
        description = "API encargada de la gestión de clientes registrados en el sistema."
)
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(
            summary = "Listar clientes",
            description = "Obtiene el listado completo de clientes registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clientes obtenidos correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(clienteService.findDTOList());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar cliente por ID",
            description = "Obtiene la información de un cliente específico utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID del cliente", example = "1")
            @PathVariable Long id){
        ClienteDTO cliente = clienteService.findById(id);

        if(cliente == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    @Operation(
            summary = "Registrar cliente",
            description = "Permite registrar un nuevo cliente en la base de datos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> registrar(@Valid @RequestBody Cliente cliente){
        Cliente clienteNuevo = clienteService.save(cliente);
        return new ResponseEntity<>(clienteNuevo,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar cliente",
            description = "Actualiza los datos de un cliente existente mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID del panadero a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Cliente cliente){

        Cliente clienteActualizado =
                clienteService.update(id,cliente);

        if(clienteActualizado == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina un cliente registrado en el sistema utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> borrar(
            @Parameter(description = "ID del panadero a actualizar", example = "1")
            @PathVariable Long id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
}
    }