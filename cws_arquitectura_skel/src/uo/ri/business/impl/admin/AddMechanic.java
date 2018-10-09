package uo.ri.business.impl.admin;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<Void> {

	private MechanicDto dto;
	private MecanicoRepository r = Factory.repository.forMechanic();

	public AddMechanic(MechanicDto mecanico) {
		this.dto = mecanico;
	}

	/**
	 * Añade un mecánico al repositorio de mecánicos
	 */
	public Void execute() throws BusinessException {
		Mecanico m = DtoAssembler.toEntity( dto );

		r.add( m );

		return null;
	}

	

}
