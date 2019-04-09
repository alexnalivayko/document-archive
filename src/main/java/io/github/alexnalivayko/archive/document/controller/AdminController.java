package io.github.alexnalivayko.archive.document.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class AdminController {

    @GetMapping("/dashboard/admin")
    public String adminMainPage(Map model) {
        return "/dashboard/admin";
    }
}
