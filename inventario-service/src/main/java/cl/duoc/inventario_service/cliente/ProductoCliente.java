package cl.duoc.inventario_service.cliente;

import cl.duoc.inventario_service.dto.ProductosDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCTOS-SERVICE")
public interface ProductoCliente {

    @GetMapping("/api/v1/productos/{id}")
    ProductosDTO buscarProductoPorID(@PathVariable("id") Long id);

}