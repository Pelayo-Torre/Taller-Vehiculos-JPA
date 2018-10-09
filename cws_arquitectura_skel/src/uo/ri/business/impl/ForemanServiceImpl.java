package uo.ri.business.impl;

import java.util.List;

import uo.ri.business.ForemanService;
import uo.ri.business.dto.ClientDto;
import uo.ri.business.impl.foreman.AddClient;
import uo.ri.business.impl.foreman.DeleteClient;
import uo.ri.business.impl.foreman.FindAllClients;
import uo.ri.business.impl.foreman.FindClientById;
import uo.ri.business.impl.foreman.FindClientsRecommended;
import uo.ri.business.impl.foreman.UpdateClient;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

public class ForemanServiceImpl implements ForemanService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	/**
	 * Añade un cliente nuevo al sistema.
	 * 
	 * @param c,
	 *            los datos del cliente que se va a añadir al sistema.
	 * @param recomenderID,
	 *            ID del cliente recomendador si el cliente viene recomendado
	 */
	public void addClient(ClientDto c, Long recomenderId) throws BusinessException {
		executor.execute( new AddClient(c, recomenderId) );
	}

	@Override
	/**
	 * Busca un cliente en el sistema por su ID.
	 * 
	 * @return los datos del cliente buscado.
	 */
	public ClientDto findClientById(Long id) throws BusinessException {
		return executor.execute( new FindClientById( id ) );
	}

	@Override
	/**
	 * Actualiza un cliente en el sistema.
	 * 
	 * @param c,
	 *            datos del cliente que se va a actualizar.
	 */
	public void updateClient(ClientDto c) throws BusinessException {
		executor.execute( new UpdateClient( c ) );
	}

	@Override
	/**
	 * Elimina un cliente en el sistema a partir de su id.
	 * 
	 * @param ID
	 *            del cliente que se va a eliminar.
	 */
	public void deleteClient(Long id) throws BusinessException {
		executor.execute( new DeleteClient( id ) );
	}

	@Override
	/**
	 * Devuelve una lista con todos los clientes del sistema.
	 * 
	 * @return lista con los datos de todos los clientes del sistema.
	 */
	public List<ClientDto> findAllClients() throws BusinessException {
		return executor.execute( new FindAllClients() );
	}

	@Override
	/**
	 * Devuelve una lista de clientes recomendados por un cliente dado.
	 * 
	 * @param id,
	 *            Id del cliente recomendador.
	 * @return lista de los datos de los clientes recomendados por el recomendador
	 */
	public List<ClientDto> findRecomendedClientsByClienteId(Long id) throws BusinessException {
		return executor.execute( new FindClientsRecommended( id ) );
	}

}
