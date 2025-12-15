package com.bank.controller;


import java.sql.Connection;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.database.DBConnection;

@Controller
public class SignupController {
	
	@GetMapping("/signup")
	public String showsignupPage() {
		return "signup";
	}
	
	@PostMapping("/signup-step1")
	public String saveStep1(
			@RequestParam String name,
			@RequestParam String fname,
			@RequestParam String dob,
			@RequestParam String gender,
			@RequestParam String email,
			@RequestParam String marital,
			Model model	
			) {
        // Store data temporarily (session)
		model.addAttribute("name", name);
		model.addAttribute("fname", fname);
		model.addAttribute("dob", dob);
		model.addAttribute("gender", gender);
		model.addAttribute("email", email);
		model.addAttribute("marital", marital);
		
        // Move to next signup page (Step 2)
		return "signup2";
	}
	
	@PostMapping("/signup-step2")
	public String saveStep2(
			@RequestParam String address,
			@RequestParam String city,
			@RequestParam String state,
			@RequestParam String pincode,
			Model model	
			) {
        // Store data temporarily (session)
		model.addAttribute("address", address);
		model.addAttribute("city", city);
		model.addAttribute("state", state);
		model.addAttribute("pincode", pincode);
		
        // Move to next signup page (Step 2)
		return "signup3";
	}
	
	@PostMapping("/signup-complete")
	public String completeSignup(
			@RequestParam("account_type") String account_type,
			@RequestParam(required = false) String[] services,
			Model model
			) {
		try {
			//	Generate card number & PIN
			long minCard = 5040936000000000L;
			long maxCard = 5040936999999999L;
			long cardNo = minCard + (long)(Math.random() * (maxCard - minCard));
			
			int pin = 1000 + (int)(Math.random() * 9000);
			
	        // Join services into a comma-separated string
			String serviceString = "";
			if(services != null) {
				serviceString = String.join(",", services);
			}
			
	        // Insert into database
			Connection conn = DBConnection.getConnection();
			Statement st = conn.createStatement();
			
			String query = "INSERT INTO signup3 (account_type, services, cardNo, pin) VALUES ('"
	                + account_type + "', '" + serviceString + "', '" + cardNo + "', '" + pin + "')";

	        st.executeUpdate(query);

	        // Show card number & pin to user
	        model.addAttribute("cardNo", cardNo);
	        model.addAttribute("pin", pin);

	        return "signup-success";
			
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database error during signup!");
			return "signup3";
		}
	}
}
