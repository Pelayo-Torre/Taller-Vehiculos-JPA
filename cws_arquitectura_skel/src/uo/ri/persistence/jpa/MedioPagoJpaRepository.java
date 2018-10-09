package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.model.Bono;
import uo.ri.model.MedioPago;
import uo.ri.model.TarjetaCredito;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class MedioPagoJpaRepository 
		extends BaseRepository<MedioPago> 
		implements MedioPagoRepository {

	@Override
	public List<MedioPago> findPaymentMeansByClientId(Long id) {
		return null;
	}

	@Override
	public List<MedioPago> findPaymentMeansByInvoiceId(Long idFactura) {
		return null;
	}

	@Override
	public List<MedioPago> findByClientId(Long id) {
		return null;
	}

	@Override
	public Object[] findAggregateVoucherDataByClientId(Long id) {
		return null;
	}

	@Override
	public TarjetaCredito findCreditCardByNumber(String pan) {
		return null;
	}

	@Override
	public List<Bono> findVouchersByClientId(Long id) {
		return null;
	}

	@Override
	/**
	 * Busca un bono a partir de su código
	 * @param code, código del bono a buscar.
	 * @return elbono si lo encuentra, null en caso contrario.
	 */
	public Bono findVoucherByCode(String code) {
		return Jpa.getManager()
				.createNamedQuery("MedioPago.findByCode", Bono.class)
				.setParameter(1, code)
				.getResultList()
				.stream()
				.findFirst().orElse( null );
	}

}
