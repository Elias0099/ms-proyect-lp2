package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.entity;

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
@Table(name = "historial_pago")
@Entity(name = "Historial_PagoEntity")
public class Historial_PagoEntity extends GenericEntity {
	
	@Id
  	@Column(name = "ID_HISTORIAL_PAGO")
  	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
  
  	@NotNull(message = "Nombre del Historial de pago es requerido")
  	@Size(min = 1, max = 120, message = "El nombre.....")
  	@Column(name = "NOMBRE")
  	private String nombre;
  	
  	@Column(name = "FECHA")
  	private String fecha;
  	
  	@Column(name = "HORA")
  	private String hora;

	@Column(name = "ID_BOLETA")
	private Long id_boleta;

}
