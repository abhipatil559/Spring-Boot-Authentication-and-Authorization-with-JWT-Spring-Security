package com.spring3.Solution.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.spring3.Solution.Model.BiddingModel;
import com.spring3.Solution.Model.UserModel;
import com.spring3.Solution.Repository.BiddingRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BiddingService {

	/*
	 * Implement the business logic for the BiddingService operations in this class
	 * Make sure to add required annotations
	 */

	@Autowired
	private BiddingRepository biddingRepository;

	@Autowired
	private UserService userService;

	// to add the Bidding using BiddingModel object
	// created->201
	// badRequest->400
	public ResponseEntity<Object> postBidding(BiddingModel biddingModel, String email) {
		UserModel u = this.userService.getUserByEmail(email);
		
		System.out.println(u.toString());
		if (u != null && u.getRole().getId() == 2) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(u);
		}

		biddingModel.setBidderId(u.getRole().getId());
		biddingModel.setDateOfBidding(gettime());
		BiddingModel b = this.biddingRepository.save(biddingModel);

//		 if (b != null) {
//		 	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(b);
//		 }

		return ResponseEntity.status(HttpStatus.CREATED).body(b);

	}

	// to get the bidding details which are greater than the given bidAmount
	// ok()->200
	// badRequest()->400
	public ResponseEntity<Object> getBidding(double bidAmount) {
		List<BiddingModel> bids = this.biddingRepository.getBiddings(bidAmount);

		if (bids.isEmpty()) {
			return ResponseEntity.badRequest().body("no data available");
		} else
			return ResponseEntity.status(HttpStatus.OK).body(bids);
	}

	// to update the bidding status
	// ok->200
	// badRequest->400
	public ResponseEntity<Object> updateBidding(int id, BiddingModel model, String email) {

		UserModel u = this.userService.getUserByEmail(email);

		if (u != null && u.getRole().getId() == 1) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(u);
		}

		Optional<BiddingModel> b = this.biddingRepository.findById(id);

		if (b.isPresent()) {
			BiddingModel bid = b.get();
			bid.setStatus(model.getStatus());
			return ResponseEntity.status(HttpStatus.OK).body(this.biddingRepository.saveAndFlush(bid));
		}

		return ResponseEntity.badRequest().body(null);
	}

	// to delete the Bidding by using id
	// approver and only the creater of that particular Bidding can delete
	// noContent->204
	// badRequest->400
	// forbidden->403
	public ResponseEntity<Object> deleteBidding(int id, String email) {

		UserModel u = this.userService.getUserByEmail(email);
		Optional<BiddingModel> b = this.biddingRepository.findById(id);

		if (u != null && b.isPresent()) {
			BiddingModel biddingModel = b.get();
			if (u.getRole().getId() == 2 || biddingModel.getBidderId() == u.getId()) {
				this.biddingRepository.deleteById(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(biddingModel);
			} else
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(u);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(u);
		}

		// return null;
	}
	
	public List<BiddingModel> getAllBidding() {
		
		List<BiddingModel> list=biddingRepository.findAll();
		return list;
	}

	private String gettime() {
		String x = String.valueOf(System.currentTimeMillis());
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		long milliSeconds = Long.parseLong(x);
		Calendar calender = Calendar.getInstance();
		calender.setTimeInMillis(milliSeconds);
		return formatter.format(calender.getTime());
	}

}
