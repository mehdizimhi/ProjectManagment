package com.mehdi.SysManagment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mehdi.SysManagment.config.JwtProvider;
import com.mehdi.SysManagment.models.User;
import com.mehdi.SysManagment.repositories.UserRepository;
import com.mehdi.SysManagment.request.LoginRequest;
import com.mehdi.SysManagment.response.AuthResponse;
import com.mehdi.SysManagment.sercives.CustomeUserDetailsImpl;
import com.mehdi.SysManagment.sercives.SubscriptionService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomeUserDetailsImpl customeUserDetails;
	
	@Autowired
	private SubscriptionService subService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception{

		
		User isUserExist = userRepository.findByEmail(user.getEmail());
		
		if(isUserExist!=null) {
			throw new Exception("email already exist !");
		}
		
		User createUser = new User();
		createUser.setPassword(passwordEncoder.encode(user.getPassword()));
		createUser.setEmail(user.getEmail());
		createUser.setFullName(user.getFullName());
		
		User savedUser = userRepository.save(createUser);
		
		subService.createSub(savedUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = JwtProvider.generateToken(authentication);
		
		AuthResponse res = new AuthResponse();
		res.setMessage("signup success");
		res.setJwt(jwt);
		
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin (@RequestBody LoginRequest loginRequest){
			
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication authentication = authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = JwtProvider.generateToken(authentication);
		
		AuthResponse res = new AuthResponse();
		res.setMessage("signup success");
		res.setJwt(jwt);
		
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customeUserDetails.loadUserByUsername(username);
		if(userDetails==null) {
			throw new BadCredentialsException("invalide username");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invalide password");
		}
		
		
		
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
	
	
}
