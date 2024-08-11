package com.mehdi.SysManagment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mehdi.SysManagment.models.PlanType;
import com.mehdi.SysManagment.models.Subscription;
import com.mehdi.SysManagment.models.User;
import com.mehdi.SysManagment.sercives.SubscriptionService;
import com.mehdi.SysManagment.sercives.UserService;

@RestController
@RequestMapping("api/subscription")
public class SubscriptionController {
	
	@Autowired
	private SubscriptionService subService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user")
	public ResponseEntity<Subscription> getUserSubscription(@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserProfleByJwt(jwt);
		
		Subscription sub = subService.getUsersSubscription(user.getId());
		
		return new ResponseEntity<>(sub, HttpStatus.OK);
	}
	
	@PatchMapping("/upgrade")
	public ResponseEntity<Subscription> upgradeSubscription(@RequestHeader("Authorization") String jwt, @RequestParam PlanType planType) throws Exception {
		User user = userService.findUserProfleByJwt(jwt);
		
		Subscription sub = subService.upgradeSubscriotion(user.getId(), planType);
		
		return new ResponseEntity<>(sub, HttpStatus.OK);
	}
	
	
}
