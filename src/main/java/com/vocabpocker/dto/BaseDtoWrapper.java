package com.vocabpocker.dto;

import java.io.Serializable;

public class BaseDtoWrapper implements Serializable {

	private Long id;
	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
