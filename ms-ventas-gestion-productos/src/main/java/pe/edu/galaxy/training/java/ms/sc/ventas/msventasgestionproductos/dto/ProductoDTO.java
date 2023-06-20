package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class ProductoDTO implements Serializable{
		  
		private static final long serialVersionUID = -9807309035903996L;
		private Long id;
		private String nombre;
		private String descripcion;
		private Double precio;
		private String img;
		private String estado;
		private Long idcategoria;

}
