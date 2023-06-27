package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.receipt;

import java.util.List;

public interface BoletaService {
	
	BoletaDTO findById(Long id) throws BoletaException;
	
	List<BoletaDTO> findByLike() throws BoletaException;
	
}
