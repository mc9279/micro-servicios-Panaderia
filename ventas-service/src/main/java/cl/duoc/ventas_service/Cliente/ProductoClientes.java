package cl.duoc.ventas_service.Cliente;


import cl.duoc.ventas_service.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCTOS-SERVICE")
public interface ProductoClientes {
    @GetMapping("/api/v1/productos/{id}")
    ProductoDTO buscarProductoPorId(@PathVariable("id") Long id);
}
