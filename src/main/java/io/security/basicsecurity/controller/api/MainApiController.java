package io.security.basicsecurity.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/main")
@RestController
public class MainApiController {

    @GetMapping
    public String main() {
        return "main string";
    }
}
