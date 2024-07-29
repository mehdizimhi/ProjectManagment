package com.mehdi.SysManagment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mehdi.SysManagment.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);
}
