package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.service.category.CategoriaDTO;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@JsonPropertyOrder(value = {"id","nombre","descripcion","precio","img","idcategoria"})

public class ProductoDTO extends GenericDTO{
		  
		private Long id;
		private String nombre;
		private String descripcion;
		private Double precio;
		private String img;
		private Long idcategoria;
		private CategoriaDTO categoria;

}
