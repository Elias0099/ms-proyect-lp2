package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.entity.ProductoEntity;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long>{
	
	@Query("select p from ProductoEntity p where p.estado='1'")
	List<ProductoEntity> getAllActivos();

}
