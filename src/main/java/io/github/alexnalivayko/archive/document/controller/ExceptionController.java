package io.github.alexnalivayko.archive.document.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@ControllerAdvice
public class ExceptionController {

	@RequestMapping(value = "/403", method = {RequestMethod.POST, RequestMethod.GET})
	public String accessDenied() {
		log.error("Access denied!");

		return "access-denied";
	}

	@ExceptionHandler(Exception.class)
	public String handleError(Exception e, Model model) {
		log.error("Exception has been caught: ", e);

		model.addAttribute("exception", e);

		return "/error";
	}
}