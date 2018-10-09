package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.FacturaRepository;
import uo.ri.model.Factura;
import uo.ri.persistence.jpa.util.BaseRepository;

public class FacturaJpaRepository 
		extends BaseRepository<Factura> 
		implements FacturaRepository {

	@Override
	public Factura findByNumber(Long numero) {
		return null;
	}

	@Override
	public Long getNextInvoiceNumber() {
		return null;
	}

	@Override
	public List<Factura> findUnusedWithBono500() {
		return null;
	}

}
