package com.vocabpocker.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public class DatatableDto {

	@JsonView(Views.Public.class)
	private List data;

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
}
