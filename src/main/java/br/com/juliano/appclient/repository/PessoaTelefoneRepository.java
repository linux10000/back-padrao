package br.com.juliano.appclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.juliano.appclient.model.PessoaTelefone;
import br.com.juliano.appclient.repository.custom.CustomPessoaTelefoneRepository;

@Repository
public interface PessoaTelefoneRepository extends JpaRepository<PessoaTelefone, Integer>, CustomPessoaTelefoneRepository  {

}
