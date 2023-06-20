package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.rest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Message {
	private Integer code;
	private String  message;
}
