package br.com.juliano.appclient.repository.custom.h2;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.juliano.appclient.model.PessoaTelefone;
import br.com.juliano.appclient.repository.custom.CustomPessoaTelefoneRepository;
import br.com.juliano.appclient.repository.to.PessoaTelefoneWithPessoaOutputFilter;
import br.com.juliano.appclient.structure.exception.business.InvalidOrderByColumnException;

@Repository
public class CustomPessoaTelefoneRepositoryImpl implements CustomPessoaTelefoneRepository {
	
	private static final List<String> _VALID_ORDER_BY_COLUMNS = Arrays.asList("ptlnid", "ptlcnumero", "pesnid", "pescnome", "pescemail");
	private static final String _ORDER_BY = "order by %s %s ";
	private static final String _LIMIT_OFFSET = " limit %d offset %d ";

	@Autowired
	private EntityManager em;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<PessoaTelefone> filterWithPessoa(
			Integer ptlnid,
			String ptlcnumero,
			Integer pesnid, 
			String pescnome, 
			String pescemail,
			String quickSearch,			
			Pageable pag
			) {
		
		Order order = pag.getSort().iterator().next();
		if ( !_VALID_ORDER_BY_COLUMNS.contains(order.getProperty()) )
			throw new InvalidOrderByColumnException();
		
		TypedQuery<PessoaTelefone> qry = em.createQuery(
				_QRY_FILTER_WITH_PESSOA				
				+ String.format(_ORDER_BY, order.getProperty(), order.getDirection().name()),
				PessoaTelefone.class
				);
		
		qry.setParameter("ptlnid", ptlnid);
		qry.setParameter("ptlcnumero", ptlcnumero);
		qry.setParameter("pesnid", pesnid);
		qry.setParameter("pescnome", pescnome);
		qry.setParameter("pescemail", pescemail);
		qry.setParameter("quickSearch", quickSearch);
		
		return qry.setFirstResult(Long.valueOf(pag.getOffset()).intValue()).setMaxResults(pag.getPageSize()).getResultList();
	}
	
	
	@Override
	public List<PessoaTelefoneWithPessoaOutputFilter> filterNativeWithPessoa(
			Integer ptlnid,
			String ptlcnumero,
			Integer pesnid, 
			String pescnome, 
			String pescemail,
			String quickSearch,			
			Pageable pag
			) {
		
		Order order = pag.getSort().iterator().next();
		if ( !_VALID_ORDER_BY_COLUMNS.contains(order.getProperty()) )
			throw new InvalidOrderByColumnException();
		
		return jdbcTemplate.query(
				_QRY_NATIVE_FILTER_WITH_PESSOA 
				+ String.format(_ORDER_BY, order.getProperty(), order.getDirection().name()) 
				+ String.format(_LIMIT_OFFSET, pag.getPageSize(), pag.getOffset()),
				new MapSqlParameterSource()
					.addValue("ptlnid", ptlnid)
					.addValue("ptlcnumero", ptlcnumero)
					.addValue("pesnid", pesnid)
					.addValue("pescnome", pescnome)
					.addValue("pescemail", pescemail)
					.addValue("quickSearch", quickSearch)
				, 
				new BeanPropertyRowMapper<PessoaTelefoneWithPessoaOutputFilter>(PessoaTelefoneWithPessoaOutputFilter.class)
				);
	}
	
	static final String _QRY_FILTER_WITH_PESSOA = """
			Select ptl
			from PessoaTelefone ptl
			inner join fetch ptl.ptlnpesFK pes
			where  (cast(ptl.ptlnid as string) = :ptlnid or :ptlnid is null)
			   and (ptl.ptlcnumero = :ptlcnumero or :ptlcnumero is null)
			   and (cast(pes.pesnid as string) = :pesnid or :pesnid is null)
			   and (pes.pescnome = :pescnome or :pescnome is null)
			   and (pes.pescemail = :pescemail or :pescemail is null)
			   and (
			      :quickSearch is null
		       or cast(ptl.ptlnid as string) like :quickSearch
		       or lower(ptl.ptlcnumero) like concat('%', lower(:quickSearch), '%')
			   or cast(pes.pesnid as string) like :quickSearch
			   or lower(pes.pescnome) like concat('%', lower(:quickSearch), '%')
			   or lower(pes.pescemail) like concat('%', lower(:quickSearch), '%')
			   )
			""";
	
	
	static final String _QRY_NATIVE_FILTER_WITH_PESSOA = """
			Select pes.pesnid, pes.pescnome
			      ,pes.PESCINFOCONFID as infoConfidencia, pes.PESCEMAIL, ptl.ptlnid
			      ,ptl.PTLCNUMERO, ptl.ptlntipo as tipo
			from Pessoa_telefone ptl
			    ,Pessoa pes
			where pes.pesnid = ptl.ptlnpes
			   and (ptl.ptlnid = :ptlnid or :ptlnid is null)
			   and (ptl.PTLCNUMERO = :ptlcnumero or :ptlcnumero is null)
			   and (pes.pesnid = :pesnid or :pesnid is null)
			   and (pes.pescnome = :pescnome or :pescnome is null)
			   and (pes.pescemail = :pescemail or :pescemail is null)
			   and (
			      :quickSearch is null
			        or cast(ptl.ptlnid as text) ilike :quickSearch
			        or ptl.PTLCNUMERO ilike '%' || :quickSearch || '%'
					or cast(pes.pesnid as text) ilike :quickSearch
					or pes.pescnome ilike '%' || :quickSearch || '%'
					or pes.pescemail ilike '%' || :quickSearch || '%'
			   )
			""";
}
