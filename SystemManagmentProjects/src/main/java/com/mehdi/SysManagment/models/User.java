package com.mehdi.SysManagment.models;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String fullName;
	private String email;
	private String password;
	private int projectSize;
	
	@JsonIgnore
	@OneToMany(mappedBy = "assignee" , cascade = CascadeType.ALL)
	private List<Issue> assignedIssues = new ArrayList();
	

}
