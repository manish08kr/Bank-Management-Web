package com.bank.controller;

import org.springframework.ui.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bank.database.DBConnection;

public class FastCashController {

	public String showFastCashPage() {
		return "fastCash";
	}

	public String fastCashWithdraw(String amount, String pin, Model model) {
		try {
			Connection con = DBConnection.getConnection();

			// validate PIN
			String pinQuery = "SELECT FROM login where pin = ?";

			PreparedStatement pst1 = con.prepareStatement(pinQuery);
			pst1.setString(1, pin);

			ResultSet rs = pst1.executeQuery();
			if (!rs.next()) {
				model.addAttribute("error", "Invalid pin");
				return "fastCash";
			}

			String cardNo = rs.getString("cardNo");

			// Calculate current balance
			String balQuery = "SELECT type, amount FROM bank WHERE cardNo =?";
			PreparedStatement pst2 = con.prepareStatement(pinQuery);
			pst2.setString(1, cardNo);

			ResultSet rs2 = pst2.executeQuery();

			int balance = 0;
			while (rs2.next()) {
				if (rs2.getString("type").equals("Deposit")) {
					balance += Integer.parseInt(rs2.getString("amount"));
				} else {
					balance -= Integer.parseInt(rs2.getString("amount"));
				}
			}

			int withdrawAmount = Integer.parseInt(amount);

			// Check insufficient balance
			if (balance < withdrawAmount) {
				model.addAttribute("error", "Insufficient Balance!");
				return "fastcash";
			}

			// Insert withdrawal as Fast Cash
			String insertQuery = "INSERT INTO bank(cardno, date, type, amount) VALUES(?, NOW(), 'FastCash', ?)";

			PreparedStatement pst3 = con.prepareStatement(insertQuery);
			pst3.setString(1, cardNo);
			pst3.setString(2, amount);

			pst3.executeUpdate();

			model.addAttribute("success", "Rs" + amount + " withdrawn successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database Error!");
		}

		return "fastcash";
	}
}
