package com.jobjunction.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String email;
	private String location;
	private String industry;
	private Integer Size;
	private Boolean productBased;
	private Boolean serviceBased;

	@OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
	private List<Job> jobs;

	public Employer(String name, String email, String location, String industry, Integer size, Boolean productBased,
			Boolean serviceBased) {
		super();
		this.name = name;
		this.email = email;
		this.location = location;
		this.industry = industry;
		this.Size = size;
		this.productBased = productBased;
		this.serviceBased = serviceBased;
	}

}
