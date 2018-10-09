package uo.ri.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = "INTERVENCION_ID, REPUESTO_ID") }
, name = "TSUSTITUCIONES")
public class Sustitucion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

	@ManyToOne private Repuesto repuesto;
	@ManyToOne private Intervencion intervencion;
	private int cantidad;

	public Sustitucion(Repuesto repuesto, Intervencion intervencion) {
		this.repuesto = repuesto;
		this.intervencion = intervencion;
		Association.Sustituir.link(this, intervencion, repuesto);
	}

	Sustitucion() {}

	public Long getId() {
		return id;
	}

	public Repuesto getRepuesto() {
		return repuesto;
	}

	void _setRepuesto(Repuesto repuesto) {
		this.repuesto = repuesto;
	}

	public Intervencion getIntervencion() {
		return intervencion;
	}

	void _setIntervencion(Intervencion intervencion) {
		this.intervencion = intervencion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intervencion == null) ? 0 : intervencion.hashCode());
		result = prime * result + ((repuesto == null) ? 0 : repuesto.hashCode());
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
		Sustitucion other = (Sustitucion) obj;
		if (intervencion == null) {
			if (other.intervencion != null)
				return false;
		} else if (!intervencion.equals(other.intervencion))
			return false;
		if (repuesto == null) {
			if (other.repuesto != null)
				return false;
		} else if (!repuesto.equals(other.repuesto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sustitucion [cantidad=" + cantidad + "]";
	}

	/**
	 * Calcula el importe de una sustitución
	 * 
	 * @return importe de la sustitución
	 */
	public double getImporte() {
		return repuesto.getPrecio() * cantidad;
	}

}
