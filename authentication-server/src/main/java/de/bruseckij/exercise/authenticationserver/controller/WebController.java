package de.bruseckij.exercise.authenticationserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ROLE_admins')")
	public String adminPage() {
		return "admin";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('ROLE_users')")
	public String userPage() {
		return "user";
	}

	@GetMapping("/oauth-login")
	public String getLoginPage() {
		return "oauth-login";
	}

	@GetMapping("/access-denied")
	public String accessDeniedPage() {
		return "access-denied";
	}

	@GetMapping("/")
	public String index(Model model, OAuth2AuthenticationToken authentication) {
		model.addAttribute("userName", authentication.getName());
		return "index";
	}

}
