package com.spring3.Solution.Controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring3.Solution.Model.JwtResponse;
import com.spring3.Solution.Security.JWTUtil;
import com.spring3.Solution.Service.LoginService;
import com.spring3.Solution.dto.LoginDTO;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api
public class LoginController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	LoginService loginService;

	@Autowired
	private JWTUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO authenticationRequest) throws Exception {

		try {
			this.authManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		} catch (Exception e) {
			// TODO: handle exception
			// UserDetails user = jwtTokenUtil.generateToken(null)
			System.out.print(e.toString());
			return ResponseEntity.badRequest().body("Invalid Credentials");
		}

//		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
//				authenticationRequest.getEmail(), authenticationRequest.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String token = jwtTokenUtil.generateToken(authentication);
////		    authenticationRequest.setToken(token);
////		    authenticationRequest.setRole(UserRepo.findByUsername(loginDto.getUsername()));
////		        return ResponseEntity.ok(loginDto);
//
	UserDetails u = this.loginService.loadUserByUsername(authenticationRequest.getEmail());
		String jwt = this.jwtTokenUtil.generateToken(u);
		JwtResponse j = new JwtResponse(200, jwt);
		return ResponseEntity.ok(j);
	}

}
