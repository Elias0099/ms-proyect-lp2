package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Response {

	private Message message;
	
	//@JsonIgnore
	private Object data;
	
	
}
