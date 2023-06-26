package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.category;

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
@JsonPropertyOrder(value = {"id","nombre","descripcion","estado"})
public class CategoriaDTO {

	private Long id;
    private String nombre;
    private String descripcion;
    private String estado;

	
}
