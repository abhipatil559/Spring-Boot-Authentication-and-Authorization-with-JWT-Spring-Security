package com.spring3.Solution.Model;

public class JwtResponse {

	private String jwt;
	private int status;
	
	public JwtResponse(int i, String jwt2) {
		// TODO Auto-generated constructor stub
		this.status=i;
		this.jwt=jwt2;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
