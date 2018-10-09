package uo.ri.business.impl.foreman;

import uo.ri.business.dto.ClientDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.util.exception.BusinessException;

public class FindClientById implements Command<ClientDto>{

	private Long id;
	
	private ClienteRepository cr = Factory.repository.forCliente();
	
	public FindClientById(Long id) {
		this.id = id;
	}

	@Override
	/**
	 * Busca un cliente por su ID
	 */
	public ClientDto execute() throws BusinessException{
		Cliente cliente = cr.findById( id );
		return cliente == null ? null : DtoAssembler.toDto( cliente );
	}

}
