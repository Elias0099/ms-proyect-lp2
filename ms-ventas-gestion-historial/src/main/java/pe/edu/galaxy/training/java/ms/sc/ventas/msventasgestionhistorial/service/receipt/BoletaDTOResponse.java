package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.receipt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.commons.Message;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoletaDTOResponse {

	private Message message;
	
	private BoletaDTO data;
	
}
