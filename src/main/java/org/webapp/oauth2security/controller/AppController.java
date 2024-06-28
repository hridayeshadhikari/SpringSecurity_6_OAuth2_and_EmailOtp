package org.webapp.oauth2security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AppController {

    @GetMapping("/home")
    public String home()
    {
        return "this is home page";
    }


    @GetMapping("/user")
    public String userPage()
    {
        return "this is user page";
    }

}
