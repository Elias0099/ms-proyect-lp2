package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionboletas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@Table(name = "detalle_boleta")
@Entity(name = "Detalle_BoletaEntity")
public class Detalle_BoletaEntity extends GenericEntity {

	@Id
	@Column(name = "ID_DETALLE_BOLETA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Unidad es requerida")
	@Column(name = "UNIDAD")
	private String unidad;

	@Column(name = "ID_BOLETA")
	private Long id_boleta;

	@Column(name = "ID_PRODUCTO")
	private Long id_producto;


}
