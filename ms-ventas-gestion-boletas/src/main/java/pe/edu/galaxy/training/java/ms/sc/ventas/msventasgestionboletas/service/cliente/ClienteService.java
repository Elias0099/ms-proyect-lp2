package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.cliente;

import java.util.List;

public interface ClienteService {

	ClienteDTO findById(Long id) throws ClienteException;
	
	List<ClienteDTO> findByLike() throws ClienteException;
	
}
