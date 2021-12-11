package io.security.basicsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/login")
@Controller
public class LoginController {

    @GetMapping
    public String login(@RequestParam(name = "e", required = false) String e,
                        Model model) {
        model.addAttribute("e", e);
        return "login";
    }
}
