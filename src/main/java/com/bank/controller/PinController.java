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
public class PinController {

	@GetMapping("/changepin") // form-action name
	public String showPinPage(HttpSession session) {

		String cardNo = (String) session.getAttribute("cardNo");
		if (cardNo == null) {
			return "redirect:/login";
		}

		return "changepin";
	}

	@PostMapping("/changepin")
	public String changePin(@RequestParam String oldpin, @RequestParam String newpin, @RequestParam String confirmpin,
			HttpSession session, Model model) {

		String cardno = (String) session.getAttribute("cardNo");

		if (cardno == null) {
			return "redirect:/login";
		}

		// new pin confirmation
		if (!newpin.equals(confirmpin)) {
			model.addAttribute("error", "New PIN and Confirm PIN do not match!");
			return "pinChange"; // JSP page name
		}

		// Length check
	    if (newpin.length() != 4) {
	        model.addAttribute("error", "PIN must be exactly 4 digits");
	        return "changePin";
	    }
	    
		try {

			Connection con = DBConnection.getConnection();

			// validate old pin
			String pinQuery = "SELECT * FROM login WHERE cardNo=? AND pin=?";

			PreparedStatement pst1 = con.prepareStatement(pinQuery);
			pst1.setString(1, cardno);
			pst1.setString(2, oldpin);

			ResultSet rs = pst1.executeQuery();

			if (!rs.next()) {
				model.addAttribute("error", "Old PIN is incorrect!");
				return "changePin";
			}

			// update pin in login table
			String updatePin = "UPDATE login SET pin = ? WHERE cardNo = ?";

			PreparedStatement pst2 = con.prepareStatement(updatePin);
			pst2.setString(1, newpin);
			pst2.setString(2, cardno);

			pst2.executeUpdate();

			model.addAttribute("success", "PIN change successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database Error!");
		}

		return "pinChange";
	}
}