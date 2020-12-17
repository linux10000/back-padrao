package br.com.juliano.appclient.repository.to;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Validated
public abstract class BaseInputFilterTO {

	@NotNull
	@Min(0)
	private Integer pageIndex;
	
	@NotNull
	@Min(10)
	@Max(100)
	private Integer pageSize;
	
	@Size(min = 0, max = 100)
	private String quickSearch;
	
	@Pattern(regexp = "(asc|desc)", flags = Flag.CASE_INSENSITIVE)
	private String sortDirection;
	
	@Size(min = 0, max = 100)
	private String sortField;
	
}
