package cl.duoc.delivery_service.controller;

import cl.duoc.delivery_service.dto.DeliveryDTO;
import cl.duoc.delivery_service.service.DeliveryService;
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
@RequestMapping("api/v1/delivery")
@Tag(
        name = "Delivery",
        description = "API encargada de la gestión de entregas y despachos asociados a las ventas."
)
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    @Operation(
            summary = "Listar entregas",
            description = "Obtiene el listado completo de entregas registradas en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entregas obtenidas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<DeliveryDTO>> listar() {

        List<DeliveryDTO> delivery = deliveryService.findDTOList();

        return ResponseEntity.ok(delivery);
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar entrega por ID",
            description = "Obtiene la información de una entrega específica utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrega encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Entrega no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DeliveryDTO> buscarporId(
            @PathVariable Long id){
        DeliveryDTO delivery = deliveryService.findById(id);
        if(delivery==null){return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(delivery);
    }
    @PostMapping
    @Operation(
            summary = "Registrar entrega",
            description = "Permite registrar una nueva entrega asociada a una venta."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entrega registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DeliveryDTO> crear(
            @Valid @RequestBody DeliveryDTO dto){
        DeliveryDTO nuevo = deliveryService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar entrega",
            description = "Actualiza los datos de una entrega existente mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrega actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Entrega no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DeliveryDTO>actualizado(
            @PathVariable Long id,
            @RequestBody DeliveryDTO dto){
        DeliveryDTO actualizado = deliveryService.update(id,dto);
        if(actualizado== null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar entrega",
            description = "Elimina una entrega registrada en el sistema utilizando su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Entrega eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Entrega no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id){
        deliveryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
