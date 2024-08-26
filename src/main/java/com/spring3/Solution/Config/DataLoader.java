package com.spring3.Solution.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.spring3.Solution.Repository.*;
import com.spring3.Solution.Model.*;


@Component
public class DataLoader implements ApplicationRunner {


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {

		roleRepository.save(new RoleModel("BIDDER"));
		roleRepository.save(new RoleModel("APPROVER"));

		userRepository.save(new UserModel(1,"bidder1","companyOne","bidder123$","bidderemail@gmail.com", new RoleModel(1)));
		userRepository.save(new UserModel(2,"bidder2","companyTwo","bidder789$","bidderemail2@gmail.com",new RoleModel(1)));
		userRepository.save(new UserModel(3,"approver","defaultCompany","approver123$", "approveremail@gmail.com",new RoleModel(2)));
		System.out.print(userRepository.findAll());
	}

}