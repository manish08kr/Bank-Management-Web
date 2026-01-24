package com.bank.controller;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.database.DBConnection;
import com.bank.model.Transaction;

import jakarta.servlet.http.HttpSession;

@Controller
public class MiniStatementController {

	@GetMapping("/miniStatement")
	public String showStatementPage(HttpSession session) {
		String cardNo = (String) session.getAttribute("cardNo");
		if (cardNo == null) {
			return "redirect:/login";
		}
		return "miniStatement";
	}

	@PostMapping("/miniStatement")
	public String getMiniStatement(@RequestParam String pin, HttpSession session, Model model) {

		String cardno = (String) session.getAttribute("cardNo");

		if (cardno == null) {
			return "redirect:/login";
		}

		try {
			Connection con = DBConnection.getConnection();

			// PIN Validation
			String pinQuery = "SELECT * FROM login WHERE cardNo = ? AND pin = ?";

			PreparedStatement pst1 = con.prepareStatement(pinQuery);
			pst1.setString(1, cardno);
			pst1.setString(2, pin);

			ResultSet rs = pst1.executeQuery();

			if (!rs.next()) {
				model.addAttribute("error", "Invalid PIN!");
				return "miniStatement";
			}

			// Fetch last 10 transactions
			String txsQuery = """
					SELECT date, type, amount FROM bank
					WHERE cardNo = ? ORDER BY date DESC LIMIT 10
					""";

			PreparedStatement pst2 = con.prepareStatement(txsQuery);
			pst2.setString(1, cardno);

			ResultSet rs2 = pst2.executeQuery();

			List<Transaction> list = new ArrayList<>();

			while (rs2.next()) {
				Transaction tsx = new Transaction(rs2.getString("date"), rs2.getString("type"), rs2.getInt("amount"));
				list.add(tsx);
			}

			model.addAttribute("trans", list);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database Error!");
		}

		return "miniStatement";
	}
}