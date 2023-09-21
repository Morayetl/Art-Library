package com.artlibrary.artlibrary.controller;

import java.time.Duration;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.artlibrary.artlibrary.requests.LoginRequest;
import com.artlibrary.artlibrary.responses.UserResponse;
import com.artlibrary.artlibrary.service.CaptchaService;
import com.artlibrary.artlibrary.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	UserService userService;

	@Autowired
	private CaptchaService captchaService;
	
	@Value("${spring.profiles.active:Unknown}")
	private String activeProfile;
	
	private final Bucket loginBucket;

	public AuthController(){
		Bandwidth limit = Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(10)));
		this.loginBucket = Bucket4j.builder().addLimit(limit).build();
	}

	@RequestMapping(value = "/login" ,method = RequestMethod.POST)
	public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {

		boolean consume = loginBucket.tryConsume(1);
		
		if(!consume){
			return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
		}
		
		String response = request.getParameter("g-recaptcha-response");
		Boolean isValid = captchaService.isValidCaptcha(response);
		if(!isValid && activeProfile == "dev"){
			return ResponseEntity.badRequest().build();
		}

		ResponseEntity<UserResponse> userResponse = this.userService.login(loginRequest);

		UserResponse body = userResponse.getBody();

		// reset ratelimiter if login is successfull
		if(body.jwt != null){
			long resetToken = 10 - loginBucket.getAvailableTokens();
			loginBucket.addTokens(resetToken);
		}
		return userResponse;
	}

	/*@RequestMapping(value = "/sign-up" ,method = RequestMethod.POST)
	public ResponseEntity<Object> register(@Valid @RequestBody UserRequest user) {
		Set<ERole> roles = new HashSet<ERole>();
		roles.add(ERole.ROLE_USER);
		
		userService.register(user, roles);
		return ResponseEntity.ok("User registered successfully!");
	}*/
}