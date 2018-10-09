package uo.ri.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { 
		@UniqueConstraint(columnNames = "AVERIA_ID, MECANICO_ID") }
, name = "TINTERVENCIONES")
public class Intervencion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

	@ManyToOne private Averia averia;
	@ManyToOne private Mecanico mecanico;
	private int minutos;

	@OneToMany(mappedBy = "intervencion")
	private Set<Sustitucion> sustituciones = new HashSet<>();

	public Intervencion(Mecanico mecanico, Averia averia) {
		this(averia, mecanico);
	}

	public Intervencion(Averia averia, Mecanico mecanico) {
		super();
		Association.Intervenir.link(averia, this, mecanico);
	}

	Intervencion() {}

	public Intervencion(Mecanico m, Averia a, int minutes) {
		this(a, m);
		this.minutos = minutes;
	}

	public Long getId() {
		return id;
	}

	Set<Sustitucion> _getSustituciones() {
		return sustituciones;
	}

	public Set<Sustitucion> getSustituciones() {
		return new HashSet<>( sustituciones );
	}

	public Averia getAveria() {
		return averia;
	}

	void _setAveria(Averia averia) {
		this.averia = averia;
	}

	public Mecanico getMecanico() {
		return mecanico;
	}

	void _setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((averia == null) ? 0 : averia.hashCode());
		result = prime * result + ((mecanico == null) ? 0 : mecanico.hashCode());
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
		Intervencion other = (Intervencion) obj;
		if (averia == null) {
			if (other.averia != null)
				return false;
		} else if (!averia.equals(other.averia))
			return false;
		if (mecanico == null) {
			if (other.mecanico != null)
				return false;
		} else if (!mecanico.equals(other.mecanico))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Intervencion [minutos=" + minutos + "]";
	}

	/**
	 * Calcula el importe de una intervención
	 * 
	 * @return importe de la intervención
	 */
	public double getImporte() {
		double horasMinutos = this.minutos / 60.0;
		double importeTotal = this.averia.getVehiculo().getTipo().getPrecioHora() * horasMinutos;

		for (Sustitucion sustitucion : sustituciones) {
			importeTotal += sustitucion.getImporte();
		}

		return importeTotal;
	}

}
