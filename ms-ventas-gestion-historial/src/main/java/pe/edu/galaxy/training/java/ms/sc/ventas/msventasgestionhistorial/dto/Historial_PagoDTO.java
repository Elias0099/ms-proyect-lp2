package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.receipt.BoletaDTO;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@JsonPropertyOrder(value = {"id","nombre","fecha","hora","idboleta"})
public class Historial_PagoDTO extends GenericDTO {
	
	private Long id;
	private String nombre;
	private String fecha;
	private String hora;
	private Long id_boleta;
	private BoletaDTO boleta;
}
