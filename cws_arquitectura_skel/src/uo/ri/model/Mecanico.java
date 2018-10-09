package uo.ri.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TMECANICOS")
public class Mecanico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

	@Column(unique = true) private String dni;
	private String apellidos;
	private String nombre;

	@OneToMany(mappedBy = "mecanico") private Set<Intervencion> intervenciones = new HashSet<>();
	@OneToMany(mappedBy = "mecanico")private Set<Averia> asignadas = new HashSet<>();

	public Mecanico(String dni, String nombre, String apellidos) {
		this( dni );
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	public Mecanico(String dni) {
		super();
		this.dni = dni;
	}

	Mecanico() {}

	public Long getId() {
		return id;
	}

	public Set<Averia> getAsignadas() {
		return new HashSet<Averia>( asignadas );
	}

	Set<Averia> _getAsignadas() {
		return asignadas;
	}

	Set<Intervencion> _getIntervenciones() {
		return intervenciones;
	}

	public Set<Intervencion> getIntervenciones() {
		return new HashSet<Intervencion>( intervenciones );
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mecanico other = (Mecanico) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mecanico [dni=" + dni + ", apellidos=" + apellidos 
				+ ", nombre=" + nombre + "]";
	}

}
