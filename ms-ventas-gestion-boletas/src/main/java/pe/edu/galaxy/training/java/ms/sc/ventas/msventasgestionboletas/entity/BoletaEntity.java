package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.entity;

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
@Table(name = "boleta")
@Entity(name = "BoletaEntity")
public class BoletaEntity extends GenericEntity{

	@Id
	@Column(name = "ID_BOLETA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Nombre de la boleta es requerido")
	@Size(min = 1, max = 120, message = "El nombre debe tener entre 1 y 120 caracteres")
	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "FECHA")
	private String fecha;

	@Column(name = "ID_CLIENTE")
	private Long id_cliente;

}
