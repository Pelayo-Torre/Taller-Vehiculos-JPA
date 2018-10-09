package uo.ri.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import javax.persistence.Table;

import alb.util.date.DateUtil;
import uo.ri.util.exception.BusinessException;

@Entity
@Table(name = "TTARJETASCREDITO")
@DiscriminatorValue("TTarjetasCredito")
public class TarjetaCredito extends MedioPago implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(unique = true) private String numero;
	private String tipo;
	private Date validez;

	public TarjetaCredito(Cliente cliente, String numero) {
		this(numero);
		Association.Pagar.link(cliente, this);
	}

	TarjetaCredito() {}

	public TarjetaCredito(String numero) {
		super();
		this.numero = numero;
		this.validez = DateUtil.tomorrow();
		this.tipo = "UNKNOWN";
	}

	public TarjetaCredito(String numero, String tipo, Date validez) {
		this( numero );
		this.tipo = tipo;
		this.validez = validez;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getValidez() {
		return validez;
	}

	public void setValidez(Date validez) {
		this.validez = validez;
	}

	public String getNumero() {
		return numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TarjetaCredito other = (TarjetaCredito) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TarjetaCredito [numero=" + numero + ", tipo=" + tipo + ", validez=" + validez + "]";
	}

	/**
	 * Comprueba si la tarjeta de crédito es válida
	 * 
	 * @return true si la tarjeta de crédito es válida, false en caso contrario
	 */
	public boolean isValidNow() {
		if (this.validez.before( DateUtil.today() )) {
			return false;
		} else {
			return true;
		}
	}

	
	/**
	 * Pagar con tarjeta de crédito
	 * 
	 * @param i,
	 *            cantidad a pagar
	 * @throws BusinessException.
	 *             Puede ser que la fecha de la tarjeta de crédito haya caducado
	 */
	public void pagar(double i) throws BusinessException {
		if (isValidNow() == true) {
			this.acumulado += i;
		} else {
			throw new BusinessException( "No se puede pagar ya que la fecha de validez ha caducado" );
		}
	}

	@Override
	/**
	 * Comprueba si el medio de pago tarjeta de credito es valida
	 */
	protected void comprobarMedioValido(double importe) throws BusinessException {
		if (isValidNow() == false) {
			throw new BusinessException( "Tarjeta de crédito caducada" );
		}
	}

}
