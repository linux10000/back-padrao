package br.com.juliano.appclient.repository.to;

public interface PessoaWithCountOutputFilter {

	Long getTotalCount();
	Integer getPesnid();
	String getPescnome();
	String getInfoConfidencial();
	String getPescemail();

}
