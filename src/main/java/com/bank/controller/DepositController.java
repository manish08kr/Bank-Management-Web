package com.bank.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.database.DBConnection;

import jakarta.servlet.http.HttpSession;

@Controller
public class DepositController {

	@GetMapping("/deposit")
	public String showDepositPage(HttpSession session) {
		
		String cardno = (String) session.getAttribute("cardNo");
		if(cardno == null) {
			return "redirect:/login";
		}
		return "deposit";
	}

	@PostMapping("/deposit")
	public String depositAmount(@RequestParam int amount, HttpSession session, Model model) {

		// Get Card no from Session in loginController
		String cardno = (String) session.getAttribute("cardNo");

/***
 *  model.addAttribute("error", "Invalid amount");
				|
				|	model Internally use request
				↓
	request.setAttribute("error", "Invalid amount");

 */
		if (cardno == null) {
			//	Session expired. Please login Again
			return "redirect/login"; // go to the login.jsp
		}

		if (amount <= 0) {
			model.addAttribute("error", "Invalid deposit amount");
			return "deposit";
		}
		
		try {
			Connection con = DBConnection.getConnection();

			// Insert deposit transaction
			String query = "INSERT INTO bank (cardNo, type, amount) VALUES (?,?,?)";

			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, cardno);
			pst.setString(2, "Credit");
			pst.setInt(3, amount);

			pst.executeUpdate();

			model.addAttribute("success", "Amount Deposited Successfully!🙂");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "something went wrong!");
		}
		return "deposit";
	}
}