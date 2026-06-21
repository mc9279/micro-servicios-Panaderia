package cl.duoc.ventas_service.Cliente;

import cl.duoc.ventas_service.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "CLIENTES-SERVICE")
public interface ClienteClientes {
    @GetMapping("/api/v1/clientes/{id}")
    ClienteDTO buscarClientePorId(@PathVariable("id") Long id);
}
