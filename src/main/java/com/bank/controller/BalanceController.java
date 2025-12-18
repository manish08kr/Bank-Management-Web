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
public class BalanceController {

	@GetMapping("/balance")
	public String showBalancePage() {
		return "withdraw";
	}

	@PostMapping("/balance")
	public String checkBalance(@RequestParam String pin, Model model) {
		try {
			Connection con = DBConnection.getConnection();

			// Validate PIN and get card number
			String pinQuery = "SELECT cardno FROM login WHERE pin = ?";

			PreparedStatement pst1 = con.prepareStatement(pinQuery);
			pst1.setString(1, pin);

			ResultSet rs = pst1.executeQuery();

			if (!rs.next()) {
				model.addAttribute("error", "Invalid PIN!");
				return "balance";
			}

			String cardno = rs.getString("cardNo");

			// Calculate balance
			String balanceQuery = "SELECT type, amount FROM bank WHERE cardno = ?";
			
			PreparedStatement pst2 = con.prepareStatement(balanceQuery);
			pst2.setString(1, cardno);

			ResultSet rs2 = pst2.executeQuery();

			int balance = 0;

			while (rs2.next()) {
				if (rs2.getString("type").equalsIgnoreCase("Deposit") || rs2.getString("type").equalsIgnoreCase("FastCash") == false) {
					
					// Deposit adds, withdrawal subtracts
					if (rs2.getString("type").equalsIgnoreCase("Deposit")) {
						balance += Integer.parseInt(rs2.getString("amount"));
					} else {
						balance -= Integer.parseInt(rs2.getString("amount"));
					}
				}
			}

			model.addAttribute("balanceMessage", "Your Current Balance is: ₹" + balance);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database Error!");
		}

		return "balance";
	}

}
