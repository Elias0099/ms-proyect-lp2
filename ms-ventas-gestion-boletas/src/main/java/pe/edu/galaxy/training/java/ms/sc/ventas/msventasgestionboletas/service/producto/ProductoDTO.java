package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.service.producto;

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
@JsonPropertyOrder(value = {"id","nombre","descripcion","precio","img","estado","idcategoria"})
public class ProductoDTO {
	
	private Long id;
	private String nombre;
	private String descripcion;
	private Double precio;
	private String img;
	private String estado;
	private Long idcategoria;

}
