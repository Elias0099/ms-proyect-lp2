package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.receipt;

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
@JsonPropertyOrder(value = {"id","nombre","fecha","id_cliente","estado"})
public class BoletaDTO {

	
	private Long id;
	private String nombre;
	private String fecha;
	private Long id_cliente;
	private String estado;
	
}
