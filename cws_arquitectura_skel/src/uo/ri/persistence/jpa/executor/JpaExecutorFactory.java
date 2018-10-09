package uo.ri.persistence.jpa.executor;

import uo.ri.business.impl.CommandExecutor;
import uo.ri.business.impl.CommandExecutorFactory;

public class JpaExecutorFactory implements CommandExecutorFactory {

	@Override
	public CommandExecutor forExecutor() {
		return new JpaCommandExecutor();
	}

}
