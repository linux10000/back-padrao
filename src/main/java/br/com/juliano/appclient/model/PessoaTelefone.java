package br.com.juliano.appclient.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.juliano.appclient.enums.TipoTelefone;
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
@Table(name = "PESSOA_TELEFONE")
public class PessoaTelefone extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PTLNID", nullable = false)
	private Integer ptlnid;
	
	@Column(name = "PTLNPES", insertable = false, updatable = false)
	private Integer ptlnpes;
	
	@Column(name = "PTLCNUMERO", length = FieldLength.DESCRICAO_100, nullable = false)
	private String ptlcnumero;
	
	@Column(name = "PTLNTIPO", nullable = false)
	private TipoTelefone ptlntipoEnum;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name= "PTLNPES", nullable = false, insertable = true, updatable = true )
	private Pessoa ptlnpesFK;
	
	public PessoaTelefone(Integer ptlnid) {
		this.ptlnid = ptlnid;
	}

	@Override
	public Object getKeyValue() {
		return ptlnid;
	}
}
