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
public class BalanceController {

	@GetMapping("/balance")
	public String showBalancePage(HttpSession session) {
		
		String cardNo = (String) session.getAttribute("cardNo");
	       if (cardNo == null) {
	           return "redirect:/login";
	       }
		return "balance";
	}

	@PostMapping("/balance")
	public String checkBalance(@RequestParam String pin, HttpSession session, Model model) {
		
		String cardno = (String)session.getAttribute("cardNo");
		
		if(cardno == null) {
			model.addAttribute("error", "Session expired. Please Login again!");
			return "redirect:/login";
		}
		
		try {
			Connection con = DBConnection.getConnection();

			// PIN Validation
			String pinQuery = "SELECT * FROM login WHERE cardNo = ? AND pin = ?";

			PreparedStatement pst1 = con.prepareStatement(pinQuery);
			pst1.setString(1, cardno);
			pst1.setString(2, pin);

			ResultSet rs1 = pst1.executeQuery();

			if (!rs1.next()) {
				model.addAttribute("error", "Invalid PIN!");
				return "balance";
			}

	        // Balance calculation
			String balanceQuery = "SELECT SUM(CASE WHEN type='Credit' THEN amount ELSE -amount END) balance "
					+ "FROM bank WHERE cardNo = ?";
			
			PreparedStatement pst2 = con.prepareStatement(balanceQuery);
			pst2.setString(1, cardno);

			ResultSet rs2 = pst2.executeQuery();
			rs2.next();
			int balance = rs2.getInt("balance");

			model.addAttribute("balanceMessage", "Your Current Balance is: ₹" + balance);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Something went wrong!");
		}

		return "balance";
	}

}
