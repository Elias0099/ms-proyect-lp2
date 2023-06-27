package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.producto.ProductoDTO;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@JsonPropertyOrder(value = {"id","unidad","id_boleta","id_producto"})
public class Detalle_BoletaDTO extends GenericDTO{

	private Long id;
	private String unidad;
	private Long id_boleta;
	private Long id_producto;
	private ProductoDTO producto;

}
