package com.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;


@Controller
public class DashboardController {

	@GetMapping("/dashboard")
	public String showDashboad(HttpSession session) {

		String cardnoString = (String) session.getAttribute("cardNo");
		if (cardnoString == null) {
			return "redirect:/login";
		}

		return "dashboard";
	}

}
