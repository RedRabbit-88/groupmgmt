package com.app.web;

import com.app.domain.user.User;
import com.app.web.argumentresolver.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String homeLogin(
            @Login User loginUser,
            Model model
    ) {
        if (loginUser == null) {
            return "home";
        }

        model.addAttribute("user", loginUser);
        return "loginHome";
    }
}
