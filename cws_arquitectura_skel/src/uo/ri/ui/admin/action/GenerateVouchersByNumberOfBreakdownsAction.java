package uo.ri.ui.admin.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

public class GenerateVouchersByNumberOfBreakdownsAction implements Action {

	@Override
	/**
	 * Generar los bonos por 3 averías no usadas.
	 */
	public void execute() throws BusinessException {

		if (Console.readString("¿Desea generar los bonos? (s/n) ").equalsIgnoreCase("s")) {
			AdminService as = Factory.service.forAdmin();
			as.generateVouchers();

			Console.println( "Bonos generados satisfactoriamente" );
		}
	}

}
