package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Message {
	private Integer code;
	private String  message;
}

