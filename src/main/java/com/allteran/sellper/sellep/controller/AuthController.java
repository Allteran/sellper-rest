package com.allteran.sellper.sellep.controller;

import com.allteran.sellper.sellep.domain.User;
import com.allteran.sellper.sellep.util.ControllerUtils;
import com.allteran.sellper.sellep.validator.UserRegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class AuthController {
    @Autowired
    private UserRegistrationValidator validator;

    @GetMapping("/login")
    public String loginPage(Model model, String error) {
        if (error != null) {
            model.addAttribute("loginError", "Login error message");
        }
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String signUpUser(User userForm, BindingResult bindingResult,
                             Model model) {
        validator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getFieldErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        return "registration";
    }
}
