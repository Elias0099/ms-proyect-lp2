package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.cliente;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@JsonPropertyOrder(value = {"id","nombre","apellidos","dni","direccion","telefono","correo","estado"})
public class ClienteDTO {
	
	private Long id;
  	private String nombres;
  	private String apellidos;
  	private String dni;
  	private String direccion;
  	private String telefono;
  	private String correo;
  	private String estado;

}
