package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.producto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.commons.Message;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTOResponse {

	private Message 	message;
	private ProductoDTO 	data;
	
}
