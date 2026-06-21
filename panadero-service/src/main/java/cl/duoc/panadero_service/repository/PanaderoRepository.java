package cl.duoc.panadero_service.repository;

import cl.duoc.panadero_service.modelo.Panadero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanaderoRepository extends JpaRepository<Panadero,Long> {
}
