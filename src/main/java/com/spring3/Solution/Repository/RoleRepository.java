package com.spring3.Solution.Repository;



import com.spring3.Solution.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RoleRepository extends JpaRepository<RoleModel,Integer> {

    //Add the required annotations to make the RoleRepository
}
