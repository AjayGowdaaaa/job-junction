package com.jobjunction.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

	private String title;
	private String description;
	private float salaray;
	private String location;

	private List<String> skills;
}
