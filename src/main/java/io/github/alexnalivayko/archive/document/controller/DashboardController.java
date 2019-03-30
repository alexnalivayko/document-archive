package io.github.alexnalivayko.archive.document.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping(path = {"/", "/dashboard"})
	public String indexPage() {

		return "index";
	}
}