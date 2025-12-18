package com.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bank.database.DBConnection;

@Controller
public class FastCashController {

	@GetMapping("/fastCash")
	public String showFastCashPage() {
		return "fastCash";
	}

	@PostMapping("/fastCash")
	public String fastCashWithdraw(String amount, String pin, Model model) {
		try {
			Connection con = DBConnection.getConnection();

			// validate PIN
			String pinQuery = "SELECT * FROM login where pin = ?";

			PreparedStatement pst1 = con.prepareStatement(pinQuery);
			pst1.setString(1, pin);

			ResultSet rs = pst1.executeQuery();
			if (!rs.next()) {
				model.addAttribute("error", "Invalid pin");
				return "fastCash";
			}

			String cardno = rs.getString("cardNo");

			// Calculate current balance
			String balQuery = "SELECT type, amount FROM bank WHERE cardNo =?";
			PreparedStatement pst2 = con.prepareStatement(balQuery);
			pst2.setString(1, cardno);

			ResultSet rs2 = pst2.executeQuery();

			int balance = 0;
			while (rs2.next()) {
				String type = rs2.getString("type");
				String amtStr = rs2.getString("amount");

				// --->  NULL / EMPTY CHECK (prevents NumberFormatException)
				if (amtStr == null || amtStr.trim().isEmpty()) {
					continue; // skip this broken row
				}

				int amt = Integer.parseInt(amtStr);

				if (type.equalsIgnoreCase("Deposit")) {
					balance += amt;
				} else {
					balance -= amt;
				}
			}

			int withdrawAmount = Integer.parseInt(amount);

			// Check insufficient balance
			if (balance < withdrawAmount) {
				model.addAttribute("error", "Insufficient Balance!");
				return "fastcash";
			}

			// Insert withdrawal as Fast Cash
			String insertQuery = "INSERT INTO bank(cardNo, date, type, amount) VALUES(?, NOW(), ?, ?)";

			PreparedStatement pst3 = con.prepareStatement(insertQuery);
			pst3.setString(1, cardno);
			pst3.setString(2, "Deposit");
			pst3.setString(3, amount);

			pst3.executeUpdate();

			model.addAttribute("success", "Rs" + amount + " withdrawn successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database Error!");
		}

		return "fastCash";
	}
}
