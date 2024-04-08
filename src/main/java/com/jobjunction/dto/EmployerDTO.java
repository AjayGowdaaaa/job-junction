package com.jobjunction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployerDTO {

	private String name;
	private String email;
	private String location;
	private String industry;
	private Integer size;
	private Boolean productBased;
	private Boolean serviceBased;
	
	public EmployerDTO(String name, String email, String location, String industry, Integer size, Boolean productBased,
			Boolean serviceBased) {
		super();
		this.name = name.toUpperCase();
		this.email = email.toLowerCase();
		this.location = location;
		this.industry = industry.toUpperCase();
		this.size = size;
		this.productBased = productBased;
		this.serviceBased = serviceBased;
	}
	
	
	
}
