package br.com.juliano.appclient.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "DOMINIO")
public class Dominio extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "DOMNID", nullable = false)
	private Integer domnid;
	
	@Column(name = "DOMCTABELA", length = FieldLength.DESCRICAO_100, nullable = false)
	private String domctabela;
	
	@Column(name = "DOMCCOLUNA", length = FieldLength.DESCRICAO_100, nullable = false)
	private String domccoluna;
	
	@Column(name = "DOMCVALOR", length = FieldLength.DESCRICAO_200, nullable = false)
	private String domcvalor;
	
	@Column(name = "DOMCORDEM", nullable = false)
	private Integer domcordem;

	@Override
	public Object getKeyValue() {
		return domnid;
	}

}
