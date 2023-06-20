package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestioncategorias.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "categoria")
public class CategoriaEntity implements Serializable {
  
    private static final long serialVersionUID = -2170897015344177815L;

    @Id
    @Column(name = "ID_CATEGORIA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nombre de la categoría es requerido")
    @Size(min = 1, max = 120, message = "El nombre debe tener entre 5 y 120 caracteres")
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "ESTADO")
    private String estado;
}



