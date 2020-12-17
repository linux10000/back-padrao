package br.com.juliano.appclient.repository.custom.h2;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.com.juliano.appclient.model.Pessoa;
import br.com.juliano.appclient.repository.custom.CustomPessoaRepository;
import br.com.juliano.appclient.structure.config.JpaConfig;

@Repository
public class CustomPessoaRepositoryImpl implements CustomPessoaRepository {
	
	@PersistenceContext(unitName = JpaConfig.PERSISTENCE_UNIT_NAME)
	private EntityManager em;

//	@Override
//	public Optional<Pessoa> findById(Integer id) {
//		return em.createQuery(_QRY_FIND_BY_ID, Pessoa.class)
//				.setParameter("pesnid", id)
//				.getResultList()
//				.stream()
//				.findFirst();
//	}
//	
//	private static final String _QRY_FIND_BY_ID = """
//			Select pes
//			from Pessoa pes
//			where pes.pesnid = :pesnid
//			""";
}
