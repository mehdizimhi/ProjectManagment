package com.mehdi.SysManagment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mehdi.SysManagment.models.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{
	
	Subscription findByUserId(Long userId);
}
