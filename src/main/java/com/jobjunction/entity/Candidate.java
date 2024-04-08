package com.jobjunction.entity;

import java.time.LocalDate;
import java.util.List;

import com.jobjunction.validators.ValidGender;
import com.jobjunction.validators.ValidMobileNumber;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

	@ValidGender
	private String gender;

	@Email
	@Column(unique = true, nullable = false)
	private String email;

	@ValidMobileNumber
	@Column(unique = true, nullable = false)
	private String mobile;
	private String address;

	private String qualification;

	@ManyToMany
	@JoinTable(name = "candidate_skills", 
			   joinColumns = @JoinColumn(name = "candidate_id"), 
			   inverseJoinColumns = @JoinColumn(name = "skill_id"))
	private List<Skills> skills;

	@Lob
	private byte[] resume;
	private String resumeName;
	private String resumePath;

	@Lob
	private byte[] profilePicture;
	private String profilePictureName;
	private String profilePicturePath;

	public Candidate(String firstName, String lastName, LocalDate dateOfBirth, String gender, String email,
			String mobile, String address, String qualification, List<Skills> skills) {
		super();
		this.firstName = firstName.toUpperCase();
		this.lastName = lastName.toUpperCase();
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.email = email.toLowerCase();
		this.mobile = mobile;
		this.address = address;
		this.qualification = qualification;
		this.skills = skills;
	}

}
