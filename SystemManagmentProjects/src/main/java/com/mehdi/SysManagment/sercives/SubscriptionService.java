package com.mehdi.SysManagment.sercives;

import com.mehdi.SysManagment.models.PlanType;
import com.mehdi.SysManagment.models.Subscription;
import com.mehdi.SysManagment.models.User;

public interface SubscriptionService {

	Subscription createSub(User user);
	Subscription getUsersSubscription(Long userId) throws Exception;
	Subscription upgradeSubscriotion(Long userId, PlanType planType);
	boolean isValid(Subscription sub);
}
