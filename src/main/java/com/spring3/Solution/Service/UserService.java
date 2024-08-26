package com.spring3.Solution.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring3.Solution.Model.UserModel;
import com.spring3.Solution.Repository.RoleRepository;
import com.spring3.Solution.Repository.UserRepository;


@Service
public class UserService {

      /*
   Implement the business logic for the UserService  operations in this class
   Make sure to add required annotations
    */

	@Autowired
    private UserRepository userRepository;

	@Autowired
    private RoleRepository roleRepository;


    //get user by email
    public UserModel getUserByEmail(String email){
        return this.userRepository.findByEmail(email).orElse(null);
    }
    public UserModel getUserById(int id){
    	
    	UserModel u= userRepository.findById(id).orElse(null);
        return u;
    }
}
