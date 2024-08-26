package com.spring3.Solution.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring3.Solution.Model.BiddingModel;
import com.spring3.Solution.Security.JWTUtil;
import com.spring3.Solution.Service.BiddingService;

@RequestMapping("/bidding")
@RestController
public class BiddingController {
	
	
	@Autowired
    private BiddingService biddingService;
	
	@Autowired
	private JWTUtil jwtUtil;

    //to create a bidding using biddingModel object
    @PostMapping("/add")
    public ResponseEntity<Object> postBidding(@RequestHeader("Authorization") String jwt,@RequestBody BiddingModel biddingModel){
       
    	String email = getEmail(jwt);
    	return this.biddingService.postBidding(biddingModel,email);
    }

    private String getEmail(String auth) {
    	
    	String email =null;
    	if (auth!=null && auth.startsWith("Bearer ")) {
			email=this.jwtUtil.getUsernameFromToken(auth.substring(7));
		}

		return email;
	}

	//to get the bidding which are greater than the given bidAmount
    @GetMapping("/list")
    public ResponseEntity<Object> getBidding(@RequestParam Double bidAmount){
        return this.biddingService.getBidding(bidAmount);
    }

    //to update the bidding by id as PathVariable and bidding Object
    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> updateBidding(@RequestHeader("Authorization") String jwt ,@PathVariable int id,@RequestBody BiddingModel biddingModel){
        
    	String email = getEmail(jwt);
    	
    	return this.biddingService.updateBidding(id, biddingModel,email);
    }

    // to delete the bidding by using id as PathVariable
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteBidding(@RequestHeader("Authorization") String jwt ,@PathVariable int id){
        String email = getEmail(jwt);
    	return this.biddingService.deleteBidding(id,email);
    }

}
