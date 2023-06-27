package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.entity.Detalle_BoletaEntity;

@Repository
public interface Detalle_BoletaRepository extends JpaRepository<Detalle_BoletaEntity, Long>{
	
	@Query("select p from Detalle_BoletaEntity p where p.estado='1'")
	List<Detalle_BoletaEntity> getAllActivos();

}
