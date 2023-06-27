package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.producto;

import java.util.List;

public interface ProductoService {

	ProductoDTO findById(Long id) throws ProductoException;
	
	List<ProductoDTO> findByLike() throws ProductoException;
	
}
