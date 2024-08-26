package com.spring3.Solution.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring3.Solution.Model.*;
import com.spring3.Solution.Repository.UserRepository;
import com.spring3.Solution.Service.BiddingService;
import com.spring3.Solution.Service.DataLoadService;
import com.spring3.Solution.Service.UserService;

@RestController
public class DemoController {

	public DataLoadService s;

	@Autowired
	public UserService userService;

	@Autowired
	public BiddingService biddingService;
	
	@Autowired
	public PasswordEncoder encoder;
	
	@Autowired
	public UserRepository userRepo;
	

	@GetMapping("/public")
	public String api1() {
//		s.loadData();
		return "this is public API";

	}

	@GetMapping("/private")
	public String api2() {
		return "this is private API";
	}

	@GetMapping("/private2")
	public String api3() {
		return "this is private API 2";
	}

	@GetMapping("/getUser/{id}")
	public UserModel getUser(int id) {

		UserModel u = userService.getUserById(id);
		return u;
	}

	@GetMapping("/getBiddings")
	public List<BiddingModel> getBiddings() {
		return biddingService.getAllBidding();
	}

	@PostMapping("/register")
	public ResponseEntity<UserModel> register(@RequestBody UserModel RegisterDto) {
		

		UserModel userEntity = new UserModel();
		
		userEntity.setCompanyName("Compnay");
		userEntity.setEmail(RegisterDto.getEmail());
		userEntity.setRole(new RoleModel(1));
		userEntity.setUsername(RegisterDto.getUsername());
		userEntity.setPassword(encoder.encode(RegisterDto.getPassword()));

	

		UserModel u=userRepo.save(userEntity);
		
		return new ResponseEntity<UserModel>(u, HttpStatus.OK);
	}

}
