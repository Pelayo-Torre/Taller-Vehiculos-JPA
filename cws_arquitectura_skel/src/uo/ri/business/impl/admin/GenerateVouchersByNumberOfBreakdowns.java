package uo.ri.business.impl.admin;

import java.util.List;

import uo.ri.business.impl.Command;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Averia;
import uo.ri.model.Bono;
import uo.ri.model.Cliente;
import uo.ri.model.MedioPago;
import uo.ri.util.exception.BusinessException;

public class GenerateVouchersByNumberOfBreakdowns implements Command<Integer> {

	private MedioPagoRepository mpr = Factory.repository.forMedioPago();
	private ClienteRepository cr = Factory.repository.forCliente();

	@Override
	public Integer execute() throws BusinessException {
		List<Cliente> clientesCandidatos = cr.findWithThreeUnusedBreakdowns();
		Integer bonosGenerados = 0;

		for (int i = 0; i < clientesCandidatos.size(); i++) {
			List<Averia> averias = null;
			averias = clientesCandidatos.get(i).getAveriasBono3NoUsadas();

			int bonos = averias.size() / 3;
			int averiasUsadasBono = bonos * 3;
			bonosGenerados += bonos;

			marcarAveriasUsadas(averiasUsadasBono, averias);
			crearBono(bonos, clientesCandidatos.get(i));
		}

		return bonosGenerados;
	}

	/**
	 * Marca las averías utilizadas para generar el bono como utilizadas
	 * 
	 * @param averiasUsadasBono
	 *            número de averías que van a ser marcadas
	 * @param averias
	 *            lista de averías del cliente
	 */
	private void marcarAveriasUsadas(int averiasUsadasBono, List<Averia> averias) {
		for (int i = 0; i < averiasUsadasBono; i++) {
			averias.get(i).markAsBono3Used();
		}
	}

	/**
	 * Genera los bonos para cliente especificado
	 * 
	 * @param bonos
	 *            que se van a dar al cliente
	 * @param cliente
	 *            que va a recibir los bonos
	 */
	private void crearBono(int bonos, Cliente cliente) {
		for (int w = 0; w < bonos; w++) {
			String codigo = obtenerCodigoUltimoBono();
			codigo = obtenerCodigoNuevoBono( codigo );

			Bono b = new Bono(cliente, codigo, "Por tres averías", 20.0);
			mpr.add( b );
		}
	}
	
	/**
	 * Obtiene el código del último bono añadido a medios de pago
	 * 
	 * @return el código del bono
	 */
	private String obtenerCodigoUltimoBono() {
		List<MedioPago> medioPago = mpr.findAll();

		String codigo = "B1010";
		String codigoNuevo = "";

		for (MedioPago b : medioPago) {
			if (b instanceof Bono) {
				codigoNuevo = ((Bono) b).getCodigo();

				if (compararCodigos(codigo, codigoNuevo) == true) {
					codigo = codigoNuevo;
				}
			}

		}
		return codigo;
	}

	/**
	 * Método auxiliar para obtener el mayor código de bonos y evitar así repetir el
	 * código.
	 * 
	 * @param codigo
	 * @param codigoNuevo
	 * @return true si el nuevo código es mayor que el anterior false en caso
	 *         contrario.
	 */
	private boolean compararCodigos(String codigo, String codigoNuevo) {
		int n = obtenerNumeroCodigo( codigo );
		int n2 = obtenerNumeroCodigo( codigoNuevo );

		if (n2 > n)
			return true;
		return false;
	}

	/**
	 * Obtiene el valor numérico del código del bono
	 * 
	 * @param codigo
	 *            del bono
	 * @return valor numérico
	 */
	private int obtenerNumeroCodigo(String codigo) {
		String[] div = codigo.split( "B" );
		return Integer.parseInt( div[1] );
	}

	/**
	 * Método auxiliar para generar el código del nuevo bono a añadir.
	 * 
	 * @param codigo
	 *            del último bono añadido
	 * @return el nuevo código
	 */
	private String obtenerCodigoNuevoBono(String codigo) {
		int n = obtenerNumeroCodigo( codigo );
		codigo = "B" + String.valueOf(( n + 10 ));
		return codigo;
	}

}
