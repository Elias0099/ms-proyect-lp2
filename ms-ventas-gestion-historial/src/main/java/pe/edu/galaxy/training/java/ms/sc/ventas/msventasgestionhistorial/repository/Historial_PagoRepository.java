package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.entity.Historial_PagoEntity;

@Repository
public interface Historial_PagoRepository extends JpaRepository<Historial_PagoEntity, Long>{
	
	@Query("select p from Historial_PagoEntity p where p.estado='1'")
	List<Historial_PagoEntity> getAllActivos();

}
