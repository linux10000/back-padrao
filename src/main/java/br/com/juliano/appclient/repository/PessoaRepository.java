package br.com.juliano.appclient.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.juliano.appclient.model.Pessoa;
import br.com.juliano.appclient.repository.custom.CustomPessoaRepository;
import br.com.juliano.appclient.repository.to.PessoaWithCountOutputFilter;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer>, CustomPessoaRepository {

	Optional<Pessoa> findByPescnome(String nome);
	
	@Query(_QRY_FILTER_WITH_SPRING_COUNT)
	Page<Pessoa> filterWithSpringCount(
			@Param("pesnid") Integer pesnid, 
			@Param("pescnome") String pescnome, 
			@Param("pescemail") String pescemail,
			@Param("quickSearch") String quickSearch,
			Pageable pag);
	
	@Query(_QRY_FILTER_WITH_SPRING_COUNT)
	Slice<Pessoa> filterWithoutCount(
			@Param("pesnid") Integer pesnid, 
			@Param("pescnome") String pescnome, 
			@Param("pescemail") String pescemail,
			@Param("quickSearch") String quickSearch,
			Pageable pag);
	
	@Query(value = _QRY_NATIVE_FILTER_WITH_WINDOW_COUNT, nativeQuery = true)
	Slice<PessoaWithCountOutputFilter> filterWithNativeWindowCount(
			@Param("pesnid") Integer pesnid, 
			@Param("pescnome") String pescnome, 
			@Param("pescemail") String pescemail,
			@Param("quickSearch") String quickSearch,
			Pageable pag);
	
	
	static final String _QRY_FILTER_WITH_SPRING_COUNT = """
			Select pes
			from Pessoa pes
			where (cast(pes.pesnid as string) = :pesnid or :pesnid is null)
			   and (pes.pescnome = :pescnome or :pescnome is null)
			   and (pes.pescemail = :pescemail or :pescemail is null)
			   and (
			      :quickSearch is null
			   or cast(pes.pesnid as string) like :quickSearch
			   or lower(pes.pescnome) like concat('%', lower(:quickSearch), '%')
			   or lower(pes.pescemail) like concat('%', lower(:quickSearch), '%')
			   )
			""";
	
	//count(*) over() funciona no PostgreSQL tb
	static final String _QRY_NATIVE_FILTER_WITH_WINDOW_COUNT = """
			Select count(*) over() as totalCount, pes.pesnid, pes.pescnome
			      ,pes.PESCINFOCONFID as infoConfidencia, pes.PESCEMAIL
			from Pessoa pes
			where (pes.pesnid = :pesnid or :pesnid is null)
			   and (pes.pescnome = :pescnome or :pescnome is null)
			   and (pes.pescemail = :pescemail or :pescemail is null)
			   and (
			      :quickSearch is null
					or cast(pes.pesnid as text) ilike :quickSearch
					or pes.pescnome ilike '%' || :quickSearch || '%'
					or pes.pescemail ilike '%' || :quickSearch || '%'
			   )
			""";
}
