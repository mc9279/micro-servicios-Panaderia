package cl.duoc.ventas_service.repository;

import cl.duoc.ventas_service.modelo.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentasRepository extends JpaRepository<Ventas,Long> {
}
