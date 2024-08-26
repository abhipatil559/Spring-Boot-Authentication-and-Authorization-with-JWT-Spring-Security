package com.spring3.Solution.Repository;

import com.spring3.Solution.Model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface BiddingRepository extends JpaRepository<BiddingModel,Integer> {

    //Add the required annotations to make the BiddingRepository
	@Query("Select m From BiddingModel m where bidAmount > :bidAmount")
	List<BiddingModel> getBiddings(Double bidAmount);
}
