package cl.duoc.delivery_service.Clientes;

import cl.duoc.delivery_service.dto.VentasDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "VENTAS-SERVICE")
public interface VentaCliente {
    @GetMapping("/api/v1/ventas/{id}")
    VentasDTO buscarVentasPorId(@PathVariable("id") Long id);
}
