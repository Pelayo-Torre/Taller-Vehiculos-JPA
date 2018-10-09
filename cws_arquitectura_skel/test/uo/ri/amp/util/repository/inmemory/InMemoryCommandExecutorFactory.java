package uo.ri.amp.util.repository.inmemory;

import uo.ri.business.impl.CommandExecutor;
import uo.ri.business.impl.CommandExecutorFactory;

public class InMemoryCommandExecutorFactory implements CommandExecutorFactory {

	@Override
	public CommandExecutor forExecutor() {
		return new InMemoryCommandExecutor();
	}

}
