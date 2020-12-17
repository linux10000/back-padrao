package br.com.juliano.appclient.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.juliano.appclient.structure.constants.FieldLength;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PESSOA")
public class Pessoa extends BaseModel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PESNID", nullable = false)
	private Integer pesnid;
	
	@Column(name = "PESCNOME", length = FieldLength.DESCRICAO_100, nullable = false)
	private String pescnome;
	
	@Column(name = "PESCINFOCONFID", length = FieldLength.DESCRICAO_100)
	private String infoConfidencial;
	
	@Column(name = "PESCEMAIL", length = FieldLength.DESCRICAO_500)
	private String pescemail;
	
	//nao tenho esse cara aqui de proposito
	//vejo isso como 'o canto da sereia': ajuda no comeco mas depois atrapalha mais do que ajuda
	//tem outras formas que considero mais 'limpas' e menos invasivas de conseguir esses dados
	//private List<PessoaTelefone> telefones;
	
	public Pessoa(Integer pesnid) {
		this.pesnid = pesnid;
	}

	@Override
	public Object getKeyValue() {
		return pesnid;
	}

}
