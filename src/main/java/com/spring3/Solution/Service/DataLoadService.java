package com.spring3.Solution.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring3.Solution.Model.RoleModel;
import com.spring3.Solution.Model.UserModel;
import com.spring3.Solution.Repository.RoleRepository;
import com.spring3.Solution.Repository.UserRepository;

@Service
public class DataLoadService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	public void loadData() {

		roleRepository.save(new RoleModel("BIDDER"));
		roleRepository.save(new RoleModel("APPROVER"));

		userRepository.save(
				new UserModel(1, "bidder1", "companyOne", "bidder123$", "bidderemail@gmail.com", new RoleModel(1)));
		// userRepository.save(new
		// UserModel(2,"bidder2","companyTwo","bidder789$","bidderemail2@gmail.com",new
		// RoleModel(1)));
		userRepository.save(new UserModel(3, "approver", "defaultCompany", "approver123$", "approveremail@gmail.com",
				new RoleModel(2)));
	}

}
