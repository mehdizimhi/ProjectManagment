package com.mehdi.SysManagment.sercives;

import com.mehdi.SysManagment.models.User;

public interface UserService {
	
	User findUserProfleByJwt(String jwt) throws Exception;
	
	User findUserByEmail(String email) throws Exception;
	
	User findUserById(Long userId)  throws Exception;
	
	User updateUserProjectSize(User user, int number) throws Exception;
}
