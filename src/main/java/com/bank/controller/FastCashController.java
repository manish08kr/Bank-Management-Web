package com.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bank.database.DBConnection;

import jakarta.servlet.http.HttpSession;

@Controller
public class FastCashController {

	@GetMapping("/fastCash")
	public String showFastCashPage(HttpSession session) {
		String cardNo = (String) session.getAttribute("cardNo");
        if (cardNo == null) {
            return "redirect:/login";
        }
		return "fastCash";
	}

	@PostMapping("/fastCash")
	public String fastCashWithdraw(@RequestParam int amount, @RequestParam String pin, HttpSession session, Model model) {
		
		String cardno = (String) session.getAttribute("cardNo");
		
		if(cardno == null) {
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
				model.addAttribute("error", "Invalid pin");
				return "fastCash";
			}

			// Balance Check : check No Gap between SUM & (
			String balQuery = "SELECT SUM(CASE WHEN type ='Credit' THEN amount ELSE -amount END) balance "+
					"FROM bank WHERE cardNo =?";
			
			PreparedStatement pst2 = con.prepareStatement(balQuery);
			pst2.setString(1, cardno);

			ResultSet rs2 = pst2.executeQuery();
			
			int balance = 0;
			if (rs2.next()) {
			    balance = rs2.getInt("balance");
			}

			if (balance < amount) {
				model.addAttribute("error", "Insufficient Balance!");
				return "fastCash";
			}

			// Debit entry
			String insertQuery = "INSERT INTO bank(cardNo, type, amount) VALUES(?,?,?)";

			PreparedStatement pst3 = con.prepareStatement(insertQuery);
			pst3.setString(1, cardno);
			pst3.setString(2, "Debit");
			pst3.setInt(3, amount);

			pst3.executeUpdate();

			model.addAttribute("success", "Rs" + amount + " withdrawn successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database Error!");
		}

		return "fastCash";
	}
}
