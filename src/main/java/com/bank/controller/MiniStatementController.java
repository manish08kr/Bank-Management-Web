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

@Controller
public class MiniStatementController {

	@GetMapping("/ministatement")
	public String showStatementPage() {
		return "ministatement";
	}

	@PostMapping("/ministatement")
	public String getMiniStatement(@RequestParam String pin, Model model) {
		try {
			Connection con = DBConnection.getConnection();

			// Validate PIN 
			String pinQuery = "SELECT cardno FROM login WHERE pin = ?";

			PreparedStatement pst1 = con.prepareStatement(pinQuery);
			pst1.setString(1, pin);

			ResultSet rs = pst1.executeQuery();

			if (!rs.next()) {
				model.addAttribute("error", "Invalid PIN!");
				return "ministatement";
			}

			String cardno = rs.getString("cardNo");

			// Fetch last 10 transactions
			String txsQuery = "SELECT data, type, amount FROM bank WHERE cardNo = ? ORDER BY date DESC LIMIT 10";
			
			PreparedStatement pst2 = con.prepareStatement(txsQuery);
			pst2.setString(1, cardno);

			ResultSet rs2 = pst2.executeQuery();

			List<Transaction> list = new ArrayList<>();
			
			while(rs2.next()) {
				list.add(new Transaction(
						rs2.getString("date"),
						rs2.getString("type"),
						rs2.getString("amount")
				));
			}
			
			model.addAttribute("trans", list);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database Error!");
		}

		return "ministatement";
	}

}
