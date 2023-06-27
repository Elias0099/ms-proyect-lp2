package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.cliente.ClienteDTO;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@JsonPropertyOrder(value = {"id","nombre","fecha","id_cliente"})
public class BoletaDTO extends GenericDTO{
	
	private Long id;
	private String nombre;
	private String fecha;
	private Long id_cliente;
	private ClienteDTO cliente;

}
