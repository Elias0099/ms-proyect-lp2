package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.Message;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTOResponse {
	
	private Message 	message;
	private CategoriaDTO 	data;

}
