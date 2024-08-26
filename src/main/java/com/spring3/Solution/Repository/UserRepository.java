package com.spring3.Solution.Repository;



import com.spring3.Solution.Model.*;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<UserModel,Integer> {

    //Add the required annotations to make the UserRepository
	
	Optional<UserModel> findByEmail(String email);
	
}
