package com.jobjunction.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CandidateDTO {
	
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String gender;
	private String email;
	private String mobile;
	private String address;
	private String qualification;

	private List<String> skills;

	public CandidateDTO(String firstName, String lastName, LocalDate dateOfBirth, String gender, String email,
			String mobile, String address, String qualification, List<String> skills) {
		super();
		this.firstName = firstName.toUpperCase();
		this.lastName = lastName.toUpperCase();
		this.dateOfBirth = dateOfBirth;
		this.gender = gender.toLowerCase();
		this.email = email.toLowerCase();
		this.mobile = mobile;
		this.address = address;
		this.qualification = qualification.toUpperCase();
		this.skills = skills;
	}
	
	

}
