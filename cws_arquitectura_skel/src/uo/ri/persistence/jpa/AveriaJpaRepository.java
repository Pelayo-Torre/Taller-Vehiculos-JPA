package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.AveriaRepository;
import uo.ri.model.Averia;
import uo.ri.persistence.jpa.util.BaseRepository;

public class AveriaJpaRepository 
		extends BaseRepository<Averia> 
		implements AveriaRepository {

	@Override
	public List<Averia> findByIds(List<Long> idsAveria) {
		return null;
	}

	@Override
	public List<Averia> findNoFacturadasByDni(String dni) {
		return null;
	}

	@Override
	public List<Averia> findWithUnusedBono3ByClienteId(Long id) {
		return null;
	}

}
