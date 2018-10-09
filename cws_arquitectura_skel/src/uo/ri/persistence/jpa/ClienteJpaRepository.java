package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.ClienteRepository;
import uo.ri.model.Cliente;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class ClienteJpaRepository 
		extends BaseRepository<Cliente> 
		implements ClienteRepository {

	@Override
	/**
	 * Busca un cliente a partir de su dni
	 * @param dni del cliente a buscar
	 * @return el cliente si lo encuentra, null en caso contrario.
	 */
	public Cliente findByDni(String dni) {
		return Jpa.getManager()
				.createNamedQuery("Cliente.findByDni", Cliente.class)
				.setParameter(1, dni)
				.getResultList()
				.stream()
				.findFirst().orElse( null );
	}

	@Override
	public List<Cliente> findWithRecomendations() {
		return null;
	}

	@Override
	/**
	 * Busca clientes con 3 o más averías no usadas para bono y que estén facturadas,
	 * al igual que en el InMemory
	 * @return Lista de clientes que cumplen las condiciones.
	 */ 
	public List<Cliente> findWithThreeUnusedBreakdowns() {
		return Jpa.getManager()
				.createNamedQuery("Cliente.findWithThreeUnusedBreakdowns", Cliente.class)
				.getResultList();
	}

	@Override
	public List<Cliente> findRecomendedBy(Long id) {
		return null;
	}


}
