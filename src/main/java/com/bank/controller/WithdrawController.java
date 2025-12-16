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
public class WithdrawController {
	
	@GetMapping("/withdraw")
	public String showWithdrawPage() {
		return "withdraw";
	}
	
	@PostMapping("/withdraw")
	public String withdrawAmount(@RequestParam String amount, @RequestParam String pin, Model model) {
		try {
			Connection con = DBConnection.getConnection();
			Statement st = con.createStatement();
			
            // Validate PIN
			String pinCheck = "SELECT * FROM login WHERE pin = '" + pin + "'";
			
			ResultSet rs = st.executeQuery(pinCheck);
			
			if(!rs.next()) {
				model.addAttribute("error", "Invalid PIN!");
				return "withdraw";
			}
			
			String cardNo = rs.getString("cardNo");
			
			// Calculate available balance
			String q1 = "SELECT type, amount FROM bank WHERE cardNo = '" + cardNo + "'";
			ResultSet rs2 = st.executeQuery(q1);
			
			int balance = 0;
			while(rs2.next()) {
				if(rs2.getString("type").equals("Deposit")) {
					balance += Integer.parseInt(rs2.getString("amount"));
				} else {
					balance -= Integer.parseInt(rs2.getString("amount"));
				}
			}
			
			int withdrawAmount = Integer.parseInt(amount);
			
			// Insufficient balance check
			if(balance < withdrawAmount) {
				model.addAttribute("error", "Insufficient balance!");
				return "withdraw";
			}
			
			// Insert withdraw record
			String q2 = "INSERT INTO bank(cardNo, date, type, amount)"+ 
						"VALUES('" + cardNo + "', NOW(), 'withdraw', '" + amount+ "')";
			
			st.executeUpdate(q2);
			
			model.addAttribute("success", "Amount withdraw Successfully!");
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database Error!");
		}
		
		return "withdraw";
	}
	
}
