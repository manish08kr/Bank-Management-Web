package com.bank.controller;


import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bank.database.DBConnection;

import jakarta.servlet.http.HttpSession;

@Controller
public class SignupController {
	
	@GetMapping("/signup")
	public String showSignupPage() {
		return "signup";				// signup.jsp
	}
	
	@PostMapping("/signup-step1")
	public String signupStep1(
			@RequestParam String name,
			@RequestParam String fname,
			@RequestParam String dob,
			@RequestParam String gender,
			@RequestParam String email,
			@RequestParam String marital,
			HttpSession session	
			) {
        // Store data temporarily (session)
		session.setAttribute("name", name);
		session.setAttribute("fname", fname);
		session.setAttribute("dob", dob);
		session.setAttribute("gender", gender);
		session.setAttribute("email", email);
		session.setAttribute("marital", marital);
		
        // Move to Next page → signup step-2
		return "signup2";
	}
	
	@PostMapping("/signup-step2")
	public String signupStep2(
			@RequestParam String address,
			@RequestParam String city,
			@RequestParam String state,
			@RequestParam String pincode,
			HttpSession session
			) {
        // Store data temporarily (session)
		session.setAttribute("address", address);
		session.setAttribute("city", city);
		session.setAttribute("state", state);
		session.setAttribute("pincode", pincode);
		
        // Move to Next page → signup step-3
		return "signup3";
	}
	
	@PostMapping("/signup-complete")
	public String completeSignup(
			@RequestParam("account_type") String account_type,
			@RequestParam(required = false) String[] services,
			HttpSession session, 
			Model model,
			RedirectAttributes rd
			) {
		try {
			//	Generate card number & PIN
			long minCard = 5040936000000000L;
			long maxCard = 5040936999999999L;
			long cardNo = minCard + (long)(Math.random() * (maxCard - minCard));
			
			int pin = 1000 + (int)(Math.random() * 9000);
			
	        // Join services into a comma-separated string
			String serviceString = (services != null) ? String.join(",", services) : "";

			//	Get all previous Data from session
			String name = (String)session.getAttribute("name");
			String fname = (String)session.getAttribute("fname");
			String dob = (String)session.getAttribute("dob");
			String gender = (String)session.getAttribute("gender");
			String email = (String)session.getAttribute("email");
			String marital = (String)session.getAttribute("marital");
			
			String address = (String)session.getAttribute("address");
			String city = (String)session.getAttribute("city");
			String state = (String)session.getAttribute("state");
			String pincode = (String)session.getAttribute("pincode");

	        // Insert into database
			Connection conn = DBConnection.getConnection();

			String q1 = """
					INSERT INTO signup3 (name, fname, dob, gender, email, marital, 
					address, city, state, pincode, account_type, services)
					VALUES (?,?,?,?,?,?,?,?,?,?,?,?)
					""";
			PreparedStatement pst1 = conn.prepareStatement(q1);
			
			pst1.setString(1, name);
			pst1.setString(2, fname);
			pst1.setString(3, dob);
			pst1.setString(4, gender);
			pst1.setString(5, email);
			pst1.setString(6, marital);
			pst1.setString(7, address);
			pst1.setString(8, city);
			pst1.setString(9, state);
			pst1.setString(10, pincode);
			pst1.setString(11, account_type);
			pst1.setString(12, serviceString);


	        pst1.executeUpdate();
	        
	        String q2 = "INSERT INTO login VALUES (?,?)";
			PreparedStatement pst2 = conn.prepareStatement(q2);
			
			pst2.setString(1,  String.valueOf(cardNo));
			pst2.setString(2,  String.valueOf(pin));
			
			pst2.executeUpdate();
	
			session.invalidate();

			//	Signup success message : 
	        rd.addFlashAttribute("success", "Account created successfully! Please do Login 🙂 <br>" +
			"Card No : " + cardNo + " | PIN : " + pin);

			//	redirect to login
	        return "redirect:/login";
			
		} catch(Exception e) {
			e.printStackTrace();
			
			//	Signup Failed --> same page
			model.addAttribute("error", "Signup Failed! Please try again");
			return "signup3";
		}
	}
}