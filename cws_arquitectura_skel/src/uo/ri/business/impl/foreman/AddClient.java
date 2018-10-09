package uo.ri.business.impl.foreman;

import uo.ri.business.dto.ClientDto;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.business.repository.RecomendacionRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Cliente;
import uo.ri.model.Metalico;
import uo.ri.model.Recomendacion;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

public class AddClient implements Command<Void> {

	private ClientDto dto;
	private Long recomenderId;

	private ClienteRepository cr = Factory.repository.forCliente();
	private RecomendacionRepository rr = Factory.repository.forRecomendacion();
	private MedioPagoRepository mpr = Factory.repository.forMedioPago();

	public AddClient(ClientDto c, Long recomenderId) {
		this.dto = c;
		this.recomenderId = recomenderId;
	}

	@Override
	/**
	 * Añade un cliente al sistema
	 */
	public Void execute() throws BusinessException {
		assertDoesNotExist( dto.dni );
		Cliente c = DtoAssembler.toEntity( dto );
		cr.add( c );

		if (recomenderId != null) {
			Cliente recomendador = obtenerRecomendador( recomenderId );
			recomendadorFacturaPagada( recomendador );
			asociarRecomendacion(recomendador, c);
		}

		asociarMetalico( c );

		return null;
	}

	/**
	 * Comprueba que el cliente que se va a añadir, no exista en el sistema.
	 * 
	 * @param dni
	 *            del cliente nuevo a añadir.
	 * @throws BusinessException,
	 *             Puede ser que el cliente que se va a añadir ya exista en sistema.
	 */
	private void assertDoesNotExist(String dni) throws BusinessException {
		Cliente cliente = cr.findByDni( dni );
		Check.isNull(cliente, "Ya existe un cliente con ese dni");
	}

	/**
	 * Comprueba que el cliente recomendador no existe, y, además si existe, te
	 * devuelve el cliente recomendador. Así evito hacer dos búsquedas por id...
	 * 
	 * @param id
	 *            del cliente recoendador
	 * @return cliente recomendador
	 * @throws BusinessException,
	 *             Puede ser que el recomendador no exista y entonces debe saltar
	 *             una businnessException
	 */
	private Cliente obtenerRecomendador(Long id) throws BusinessException {
		Cliente cliente = cr.findById( id );
		Check.isNotNull(cliente, "No existe el cliente recomendador");
		return cliente;
	}

	/**
	 * Comprueba que el cliente recomendador tiene una factura abonada
	 * 
	 * @param recomendador,
	 *            del que se va a comprobar si tiene o no factura pagada
	 * @throws BusinessException,
	 *             Puede ser que el recomendador no tenga una factura abonada
	 */
	private void recomendadorFacturaPagada(Cliente recomendador) throws BusinessException {
		Check.isFalse(recomendador.clienteConFacturaAbonada(), "Recomendador sin factura abonada");
	}

	/**
	 * Se asocia un medio de pago de tipo metálico al cliente
	 * 
	 * @param cliente
	 *            al que se le dará un medio de pago metálico
	 */
	private void asociarMetalico(Cliente cliente) {
		Metalico metalico = new Metalico( cliente );
		mpr.add( metalico );
	}

	/**
	 * Asocia un cliente recomendado a un recomendador
	 * 
	 * @param recomendador,
	 *            el que recomienda
	 * @param recomendado,
	 *            el que ha sido recomendado
	 */
	private void asociarRecomendacion(Cliente recomendador, Cliente recomendado) {
		Recomendacion recomendacion = new Recomendacion(recomendador, recomendado);
		rr.add( recomendacion );
	}

}
