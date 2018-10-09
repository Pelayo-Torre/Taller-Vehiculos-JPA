package uo.ri.business.impl.foreman;

import uo.ri.business.dto.ClientDto;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.model.types.Address;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

public class UpdateClient implements Command<Void> {

	private ClientDto dto;

	private ClienteRepository cr = Factory.repository.forCliente();

	public UpdateClient(ClientDto c) {
		this.dto = c;
	}

	@Override
	/**
	 * Actualiza los datos de un cliente
	 */
	public Void execute() throws BusinessException {
		Cliente cliente = obtenerCliente( dto.id );

		cliente.setNombre( dto.name );
		cliente.setApellidos( dto.surname );
		cliente.setAddress( new Address( dto.addressStreet, dto.addressCity, dto.addressZipcode ) );
		cliente.setEmail( dto.email );
		cliente.setPhone( dto.phone );

		return null;
	}

	/**
	 * Devuelve el cliente a partir del id introducido. Comprueba también que el
	 * cliente obtenido exista en el sistema.
	 * 
	 * @param id
	 *            del cliente a actualizar
	 * @return cliente obtenido
	 * @throws BusinessException.
	 *             Puede ser que el cliente a actualizar no se encuentre registrado
	 *             en el sistema
	 */
	private Cliente obtenerCliente(Long id) throws BusinessException {
		Cliente cliente = cr.findById( id );
		Check.isNotNull(cliente, "El cliente a actualizar no existe en el sistema");
		return cliente;
	}

}
