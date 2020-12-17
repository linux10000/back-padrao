package br.com.juliano.appclient.controller.response;

import java.util.List;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class BaseFilterResponse<T> {

	private Long totalCount;
	private Integer totalPages;
	private List<T> data;
	private Integer pageIndex;
}
