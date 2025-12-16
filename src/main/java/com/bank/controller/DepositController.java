package com.bank.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
			Statement st = con.createStatement();
			
            // Check if PIN exists in login table
			String pinCheck = "SELECT * FROM login WHERE pin = '" + pin + "'";
			
			ResultSet rs = st.executeQuery(pinCheck);
			
			if(!rs.next()) {
				model.addAttribute("error", "Invalid PIN");
				return "deposit";
			}
			
			String cardNo = rs.getString("cardNo");
			
            // Insert deposit transaction
			String q1 = "INSERT INTO bank(cardNo, date, type, amount)"+ 
						"VALUES('" + cardNo + "', NOW(), 'Deposit', '" + amount+ "')";
			
			st.executeUpdate(q1);
			
			model.addAttribute("success", "Amount Deposited Successfully!");
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database Error!");
		}
		
		return "deposit";
	}
	
}
