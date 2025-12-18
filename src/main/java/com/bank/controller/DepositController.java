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

@Controller
public class DepositController {
	
	@GetMapping("/deposit")
	public String showDepositPage() {
		return "deposit";
	}
	
	@PostMapping("/deposit")
	public String depositAmount(@RequestParam String amount, @RequestParam String pin, Model model) {
		try {
			Connection con = DBConnection.getConnection();
			
			// 1. Validate PIN
			String pinQuery = "SELECT cardNo FROM login WHERE pin = ?";		
			
			PreparedStatement pst1 = con.prepareStatement(pinQuery);
			pst1.setString(1, pin);
			ResultSet rs = pst1.executeQuery();
			
			if(!rs.next()) {
				model.addAttribute("error", "Invalid PIN!");
				return "deposit";
			}
			
			String cardno = rs.getString("cardNo");
			
            // 2. Insert deposit transaction
			String insertQuery = "INSERT INTO bank(cardNo, date, type, amount) VALUES(?, NOW(), 'Deposit', ?)";
			PreparedStatement pst2 = con.prepareStatement(insertQuery);
			pst2.setString(1, cardno);
			pst2.setString(2, amount);
			
			pst2.executeUpdate();
			
			model.addAttribute("success", "Amount Deposited Successfully!");
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database Error!");
		}
		
		return "deposit";
	}
	
}
