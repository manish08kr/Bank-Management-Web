package com.bank.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.database.DBConnection;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String showLoginPage() {
		return "login"; 		// loads login.jsp
	}

	@PostMapping("/login")
	public String loginUser(
			@RequestParam String cardno, // this cardno must match with login.jsp (name ="cardno")
			@RequestParam String pin, 
			HttpSession session, 
			Model model
			) {
		try {
			Connection conn = DBConnection.getConnection();

			String query = "SELECT * FROM login WHERE cardNo = ? AND pin = ?";

			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, cardno);
			pst.setString(2, pin);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				// store CARD_NO in session	--> card no yhi se har controller me jata hai
				session.setAttribute("cardNo", cardno);
				return "dashboard"; 	// go to DashBoard
			} else {
				model.addAttribute("error", "Invalid Card Number or PIN!");
				return "login";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database error!");
			return "login";
		}
	}
}