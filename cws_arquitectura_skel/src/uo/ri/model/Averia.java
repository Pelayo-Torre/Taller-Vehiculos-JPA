package uo.ri.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.FacturaStatus;
import uo.ri.util.exception.BusinessException;

@Entity
@Table(uniqueConstraints = { 
		@UniqueConstraint(columnNames = "FECHA, VEHICULO_ID") }
, name = "TAVERIAS")
public class Averia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

	private String descripcion;
	@Temporal(TemporalType.DATE) private Date fecha;
	private double importe = 0.0;
	@Enumerated(EnumType.STRING) private AveriaStatus status = AveriaStatus.ABIERTA;

	@ManyToOne private Vehiculo vehiculo;
	@ManyToOne private Factura factura;
	@ManyToOne private Mecanico mecanico;
	@OneToMany(mappedBy = "averia") private Set<Intervencion> intervenciones = new HashSet<>();

	private boolean usada_bono = false;

	Averia() {}

	public Averia(Vehiculo vehiculo, String descripcion) {
		this( vehiculo );
		this.descripcion = descripcion;
	}

	public Averia(Vehiculo vehiculo) {
		super();
		this.vehiculo = vehiculo;
		this.fecha = new Date();
		Association.Averiar.link(vehiculo, this);
	}

	public Long getId() {
		return id;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	void _setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Factura getFactura() {
		return factura;
	}

	void _setFactura(Factura factura) {
		this.factura = factura;
	}

	public Mecanico getMecanico() {
		return mecanico;
	}

	void _setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	Set<Intervencion> _getIntervenciones() {
		return intervenciones;
	}

	public Set<Intervencion> getIntervenciones() {
		return new HashSet<Intervencion>( intervenciones );
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getImporte() {
		return importe;
	}

	public Date getFecha() {
		return (Date) fecha.clone();
	}

	public AveriaStatus getStatus() {
		return status;
	}

	public void setStatus(AveriaStatus status) {
		this.status = status;
	}

	public boolean isUsada_bono() {
		return usada_bono;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((vehiculo == null) ? 0 : vehiculo.hashCode());
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
		Averia other = (Averia) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (vehiculo == null) {
			if (other.vehiculo != null)
				return false;
		} else if (!vehiculo.equals(other.vehiculo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Averia [descripcion=" + descripcion + ", fecha=" 
				+ fecha + ", importe=" + importe + ", status=" + status
				+ "]";
	}

	/**
	 * Asigna la averia al mecanico Se cambia el estado de la avería a ASIGNADA
	 * 
	 * @param mecanico
	 *            al que se le va asigna la avería
	 */
	public void assignTo(Mecanico mecanico) {
		if (getStatus().equals( AveriaStatus.ABIERTA )) {
			Association.Asignar.link(mecanico, this);
			setStatus( AveriaStatus.ASIGNADA );
		}
	}

	/**
	 * El mecánico da por finalizada esta avería, Se calcula el importe Se
	 * desvincula el mecanico y la averia El Status de la avería pasa a TERMINADA
	 */
	public void markAsFinished() {
		if (getStatus().equals( AveriaStatus.ASIGNADA )) {
			calcularImporte();
			Association.Asignar.unlink(mecanico, this);
			setStatus( AveriaStatus.TERMINADA );
		}
	}

	/**
	 * Calcula el importe de las intervenciones de cada mecanico
	 */
	public void calcularImporte() {
		this.importe = 0.0;
		for (Intervencion i : getIntervenciones()) {
			this.importe += i.getImporte();
		}
	}

	/**
	 * Una averia en estado TERMINADA se puede asignar a otro mecánico (el primero
	 * no ha podido terminar la reparación), pero debe ser pasada a ABIERTA primero
	 * Solo se puede reabrir una avería que está TERMINADA
	 */
	public void reopen() {
		if (getStatus().equals( AveriaStatus.TERMINADA )) {
			setStatus( AveriaStatus.ABIERTA );
		}
	}

	/**
	 * Pone una avería en estado Facturada
	 * 
	 * @throws BusinessException.
	 *             Puede ser que la avería no tenga ninguna factura asignada
	 */
	public void markAsInvoiced() throws BusinessException {
		if (factura != null) {
			setStatus( AveriaStatus.FACTURADA );
		} else {
			throw new BusinessException( "No factura asignada" );
		}
	}

	/**
	 * Una avería ya facturada se elimina de la factura Se verifica que la avería
	 * está FACTURADA Se cambia el Status de la avería a TERMINADA
	 */
	public void markBackToFinished() {
		if (this.status.equals( AveriaStatus.FACTURADA )) {
			setStatus( AveriaStatus.TERMINADA );
		}
	}

	/**
	 * Desasigna una avería de un mecánico.
	 */
	public void desassign() {
		Association.Asignar.unlink(mecanico, this);
	}

	/**
	 * Una avería para ser usada para bono debe cumplir: 1. La avería se encuentra
	 * facturada 2. La factura de dicha avería se encuentra abonada 3. La avería no
	 * fue usada para bono
	 * 
	 * @return true si elegible para bono, false en caso contrario
	 */
	public boolean esElegibleParaBono3() {
		if (status.equals( AveriaStatus.FACTURADA ) 
				&& factura.getStatus().equals( FacturaStatus.ABONADA )
				&& usada_bono == false) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Marca la avería como usada para bono
	 */
	public void markAsBono3Used() {
		usada_bono = true;
	}

	/**
	 * Comprueba si la avería fue usada para bono
	 * 
	 * @return true si fue usada, false en caso contrario
	 */
	public boolean isUsadaBono3() {
		if (usada_bono == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Devuelve true si la avería está facturada
	 * 
	 * @return true si está facturada, false en caso contrario
	 */
	public boolean isInvoiced() {
		if (this.status.equals( AveriaStatus.FACTURADA )) {
			return true;
		} else {
			return false;
		}
	}

}
