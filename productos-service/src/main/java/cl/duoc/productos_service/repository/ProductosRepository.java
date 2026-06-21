package cl.duoc.productos_service.repository;

import cl.duoc.productos_service.modelo.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepository extends JpaRepository<Productos,Long> {
}
