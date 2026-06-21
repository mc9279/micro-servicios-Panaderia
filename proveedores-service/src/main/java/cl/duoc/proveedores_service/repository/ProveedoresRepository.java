package cl.duoc.proveedores_service.repository;

import cl.duoc.proveedores_service.modelo.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedores,Long> {
}
