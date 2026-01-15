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
public class WithdrawController {

	@GetMapping("/withdraw")
	public String showWithdrawPage(HttpSession session) {
		String cardNo = (String) session.getAttribute("cardNo");
		if (cardNo == null) {
			return "redirect:/login";
		}
		return "withdraw";
	}

	@PostMapping("/withdraw")
	public String withdrawAmount(@RequestParam int amount, @RequestParam String pin, HttpSession session, Model model) {

		// Get Card no from Session in loginController
		String cardno = (String) session.getAttribute("cardNo");

		if (cardno == null) {
			return "redirect:/login";
		}

		if (amount <= 0) {
			model.addAttribute("error", "Invalid amount");
			return "withdraw";
		}

		try {
			Connection con = DBConnection.getConnection();

			// Validate PIN
			String pinCheck = "SELECT * FROM login WHERE cardNo = ? AND pin = ?";

			PreparedStatement pst1 = con.prepareStatement(pinCheck);
			pst1.setString(1, cardno);
			pst1.setString(2, pin);

			ResultSet rs1 = pst1.executeQuery();

			if (!rs1.next()) {
				model.addAttribute("error", "Invalid PIN!");
				return "withdraw";
			}

			// Balance Check
			String balQuery = "SELECT SUM(CASE WHEN type = 'Credit' THEN amount ELSE -amount END) balance "
					+ "FROM bank WHERE cardNo = ?";
			PreparedStatement pst2 = con.prepareStatement(balQuery);
			pst2.setString(1, cardno);

			ResultSet rs2 = pst2.executeQuery();
			int balance = 0;
			if(rs2.next()) {
				balance = rs2.getInt("balance");
			}

			if (balance < amount) {
				model.addAttribute("error", "Insufficient balance!");
				return "withdraw";
			}

			// Debit Amount
			String debitQuery = "INSERT INTO bank(cardNo, type, amount) VALUES(?,?,?)";
			PreparedStatement pst3 = con.prepareStatement(debitQuery);
			pst3.setString(1, cardno);
			pst3.setString(2, "Debit");
			pst3.setInt(3, amount);

			pst3.executeUpdate();

			model.addAttribute("success", "Cash withdrawn Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database Error!");
		}

		return "withdraw";
	}

}
