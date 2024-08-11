package com.mehdi.SysManagment.sercives;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mehdi.SysManagment.models.PlanType;
import com.mehdi.SysManagment.models.Subscription;
import com.mehdi.SysManagment.models.User;
import com.mehdi.SysManagment.repositories.SubscriptionRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriptionRepository subRepo;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Subscription createSub(User user) {
		Subscription sub = new Subscription();
		sub.setUser(user);
		sub.setSubStartDate(LocalDate.now());
		sub.setSubEndDate(LocalDate.now().plusMonths(12));
		sub.setValid(true);
		sub.setPlanType(PlanType.FREE);
		
		return subRepo.save(sub);
	}

	@Override
	public Subscription getUsersSubscription(Long userId) throws Exception {
		Subscription sub = subRepo.findByUserId(userId);
		if(!isValid(sub)) {
			sub.setPlanType(PlanType.FREE);
			sub.setSubEndDate(LocalDate.now().plusMonths(12));
			sub.setSubStartDate(LocalDate.now());
		}
		return subRepo.save(sub);
	}

	@Override
	public Subscription upgradeSubscriotion(Long userId, PlanType planType) {
		Subscription sub = subRepo.findByUserId(userId);
		sub.setPlanType(planType);
		sub.setSubStartDate(LocalDate.now());
		if(planType.equals(PlanType.ANNUALY)) {
			sub.setSubEndDate(LocalDate.now().plusMonths(12));
		}else {
			sub.setSubEndDate(LocalDate.now().plusMonths(1));
		}
		return subRepo.save(sub);
	}

	@Override
	public boolean isValid(Subscription sub) {
		if(sub.getPlanType().equals(PlanType.FREE)) {
			return true;
		}
		LocalDate endDate = sub.getSubEndDate();
		LocalDate currentDate = LocalDate.now();
		
		
		return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
	}

}
