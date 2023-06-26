package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.rest;


import lombok.Builder;
import lombok.Data;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.commons.Message;

@Builder
@Data
public class Response {

	private Message message;
	
	//@JsonIgnore
	private Object data;
	
	
}
