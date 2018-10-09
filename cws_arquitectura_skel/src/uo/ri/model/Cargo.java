package uo.ri.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.model.types.FacturaStatus;
import uo.ri.util.exception.BusinessException;

@Entity
@Table(uniqueConstraints = { 
		@UniqueConstraint(columnNames = "FACTURA_ID, MEDIOPAGO_ID") }
, name = "TCARGOS")
public class Cargo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

	@ManyToOne private Factura factura;
	@ManyToOne private MedioPago medioPago;
	private double importe = 0.0;

	Cargo() {}

	public Long getId() {
		return id;
	}

	public Factura getFactura() {
		return factura;
	}

	void _setFactura(Factura factura) {
		this.factura = factura;
	}

	public MedioPago getMedioPago() {
		return medioPago;
	}

	void _setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((factura == null) ? 0 : factura.hashCode());
		result = prime * result + ((medioPago == null) ? 0 : medioPago.hashCode());
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
		Cargo other = (Cargo) obj;
		if (factura == null) {
			if (other.factura != null)
				return false;
		} else if (!factura.equals(other.factura))
			return false;
		if (medioPago == null) {
			if (other.medioPago != null)
				return false;
		} else if (!medioPago.equals(other.medioPago))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cargo [importe=" + importe + "]";
	}

	/**
	 * Se incrementa el importe en el acumulado del medio de pago Se guarda dicho
	 * importe Se enlaza la factura, el cargo y el medioPago.
	 * 
	 * @param factura
	 *            a enlazar
	 * @param medioPago
	 *            a enlazar
	 * @param importe
	 *            a enlazar
	 * @throws BusinessException
	 *             Puede ser que la cantidad a pagar sea mayor que la cantidad
	 *             disponible
	 */
	public Cargo(Factura factura, MedioPago medioPago, double importe) throws BusinessException {
		this.factura = factura;
		this.medioPago = medioPago;
		this.importe = importe;

		this.medioPago.cagar( importe );

		Association.Cargar.link( this );
	}

	/**
	 * Anula (retrocede) este cargo de la factura y el medio de pago Solo se puede
	 * hacer si la factura no está abonada Decrementar el acumulado del medio de
	 * pago Desenlazar el cargo de la factura y el medio de pago
	 * 
	 * @throws BusinessException
	 */
	public void rewind() throws BusinessException {
		if (factura.getStatus().equals( FacturaStatus.SIN_ABONAR )) {
			double total = this.medioPago.getAcumulado() - this.importe;
			this.medioPago.setAcumulado( total );

			Association.Cargar.unlink( this );
		}
	}

}
