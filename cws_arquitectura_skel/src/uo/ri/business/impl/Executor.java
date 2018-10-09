package uo.ri.business.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import uo.ri.persistence.jpa.util.Jpa;
import uo.ri.util.exception.BusinessException;

public class Executor {

	public <T> T execute(Command<T> comando) throws Exception {
		EntityManager mapper = Jpa.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		try {

			T res = comando.execute();

			trx.commit();

			return res;

		} catch (BusinessException | RuntimeException e) {
			trx.rollback();
			throw e;
		} finally {
			mapper.close();
		}
	}

}
