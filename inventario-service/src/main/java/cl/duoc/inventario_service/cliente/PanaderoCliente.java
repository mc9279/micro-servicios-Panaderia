package cl.duoc.inventario_service.cliente;

import cl.duoc.inventario_service.dto.PanaderoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "PANADERO-SERVICE")
public interface PanaderoCliente {

    @GetMapping("/api/v1/panadero/{id}")
    PanaderoDTO buscarPanaderoPorId(@PathVariable("id") Long id);

}