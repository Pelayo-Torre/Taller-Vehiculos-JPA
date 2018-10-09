package uo.ri.business.impl.foreman;

import java.util.ArrayList;
import java.util.List;

import uo.ri.business.dto.ClientDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.model.Recomendacion;
import uo.ri.util.exception.BusinessException;

public class FindClientsRecommended implements Command<List<ClientDto>> {

	private Long id;

	private ClienteRepository cr = Factory.repository.forCliente();

	public FindClientsRecommended(Long id) {
		this.id = id;
	}

	@Override
	/**
	 * Devuelve una lista con los clientes recomendados por un cliente dado
	 */
	public List<ClientDto> execute() throws BusinessException {
		Cliente cliente = cr.findById( id );
		List<Cliente> lista = new ArrayList<Cliente>();

		if(cliente != null) {
			for (Recomendacion m : cliente.getRecomendacionesHechas()) {
				lista.add( m.getRecomendado() );
			}
		}
		
		
		return DtoAssembler.toClientDtoList( lista );
	}

}
