package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.category;

import java.util.List;

public interface CategoriaService {
	
	CategoriaDTO findById(Long id) throws CategoriaException;
	
	List<CategoriaDTO> findByLike() throws CategoriaException;


}
