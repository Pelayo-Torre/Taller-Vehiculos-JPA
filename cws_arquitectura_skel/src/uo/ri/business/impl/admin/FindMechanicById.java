package uo.ri.business.impl.admin;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;
import uo.ri.util.exception.BusinessException;

public class FindMechanicById implements Command<MechanicDto>{

	private Long id;

	public FindMechanicById(Long id) {
		this.id = id;
	}

	/**
	 * Devuelve un mecanicoDto a partir de su ID
	 */
	public MechanicDto execute() throws BusinessException {
		MecanicoRepository r = Factory.repository.forMechanic();
		Mecanico m = r.findById( id );
		
		return m == null ? null : DtoAssembler.toDto( m );
	}



}
