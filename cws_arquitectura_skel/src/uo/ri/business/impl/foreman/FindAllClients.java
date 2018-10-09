package uo.ri.business.impl.foreman;

import java.util.List;

import uo.ri.business.dto.ClientDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.util.exception.BusinessException;

public class FindAllClients implements Command<List<ClientDto>>{

	ClienteRepository cr = Factory.repository.forCliente();

	@Override
	/**
	 * Devuelve una lista de ClientsDto
	 */
	public List<ClientDto> execute() throws BusinessException {
		List<Cliente> lista = cr.findAll();
		
		return DtoAssembler.toClientDtoList( lista );
	}

}
