package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenericDTO {

	@Default
	private String estado = "1";
	
	
}