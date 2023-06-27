package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.Message;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTOResponse {

	private Message 	message;
	private ClienteDTO 	data;
	
}
