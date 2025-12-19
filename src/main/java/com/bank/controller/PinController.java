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
public class PinController {

	@GetMapping("/changepin")			// form-action name
	public String showPinPage() {
		return "changepin";
	}

	@PostMapping("/changepin")
	public String changePin(@RequestParam String oldpin, @RequestParam String newpin, @RequestParam String confirmpin, Model model) {
		try {
			
			// new pin confirmation
			if(!newpin.equals(confirmpin)) {
				model.addAttribute("error", "New PIN and Confirm PIN do not match!");
				return "pinChange";			// JSP page name
			}

			Connection con = DBConnection.getConnection();
			
			// validate old pin
			String pinQuery = "SELECT cardno FROM login WHERE pin = ?";

			PreparedStatement pst1 = con.prepareStatement(pinQuery);
			pst1.setString(1, oldpin);

			ResultSet rs = pst1.executeQuery();

			if (!rs.next()) {
				model.addAttribute("error", "Old PIN is incorrect!");
				return "pinChange";		
			}

			String cardno = rs.getString("cardNo");

			// update pin in login table
			String updatePin = "UPDATE login SET pin = ? WHERE cardno = ?";
			
			PreparedStatement pst2 = con.prepareStatement(updatePin);
			pst2.setString(1, newpin);
			pst2.setString(1, cardno);

			pst2.executeUpdate();

			model.addAttribute("success", "PIN change successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Database Error!");
		}

		return "pinChange";
	}

}
