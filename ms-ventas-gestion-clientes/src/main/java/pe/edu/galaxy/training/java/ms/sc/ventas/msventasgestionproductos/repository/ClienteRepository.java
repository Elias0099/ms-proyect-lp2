package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.entity.ClienteEntity;


@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>{

}
