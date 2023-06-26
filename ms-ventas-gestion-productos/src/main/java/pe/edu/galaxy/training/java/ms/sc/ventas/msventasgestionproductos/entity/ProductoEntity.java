package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionproductos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "producto")
@Entity(name = "ProductoEntity")
public class ProductoEntity extends GenericEntity{

		@Id
	  	@Column(name = "ID_PRODUCTO")
	  	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
	  
	  	@NotNull(message = "Nombre del producto es requerido")
	  	@Size(min = 1, max = 120, message = "El nombre.....")
	  	@Column(name = "NOMBRE")
	  	private String nombre;
	  	
	  	@Column(name = "DESCRIPCION")
	  	private String descripcion;
	  	
	  	@Column(name = "PRECIO")
	  	private Double precio;
	  	
	  	@Column(name = "IMG")
	  	private String img;

		@Column(name = "IDCATEGORIA")
		private Long idcategoria;
}
