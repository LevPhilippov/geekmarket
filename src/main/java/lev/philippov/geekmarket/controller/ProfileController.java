package lev.philippov.geekmarket.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ProfileController {

    @GetMapping(value = "/profile")
    public String getUserProfile(Model model){

    }
}
