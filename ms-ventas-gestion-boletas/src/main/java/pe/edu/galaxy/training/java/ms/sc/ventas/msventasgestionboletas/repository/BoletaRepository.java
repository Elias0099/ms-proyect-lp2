package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.entity.BoletaEntity;

@Repository
public interface BoletaRepository extends JpaRepository<BoletaEntity, Long> {

	@Query("select p from BoletaEntity p where p.estado='1'")
	List<BoletaEntity> getAllActivos();
	
}
