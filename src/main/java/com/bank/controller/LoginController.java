package com.bank.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.database.DBConnnection;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";		// loads login.jsp
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestParam String cardNo, @RequestParam String pin, Model model) {
//		-->	will connect database here
//		System.out.println("User entered: " + cardNo + " and PIN: " + pin);
//		
//		return "home";		// temporary page (we will create ./it later)
		
		try {
			Connection conn = DBConnnection.getConnection();
			Statement st = conn.createStatement();
			String query = "SELECT * FROM login WHERE cardNo = '" + cardNo + "' AND pin = '" + pin + "'";
			
			ResultSet rs = st.executeQuery(query);

			if(rs.next()) {
				return "home";
			}else {
				model.addAttribute("error", "Invalid Card Number or PIN!");
				return "login";
			}			
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database error!");
			return "login";
		}
	}
}
