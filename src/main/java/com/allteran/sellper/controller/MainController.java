package com.allteran.sellper.controller;

import com.allteran.sellper.domain.User;
import com.allteran.sellper.repo.NomenclatureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final NomenclatureRepo nomenclatureRepo;
    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(NomenclatureRepo nomenclatureRepo) {
        this.nomenclatureRepo = nomenclatureRepo;
    }

    @GetMapping
        public String index(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {
            data.put("nomList", nomenclatureRepo.findAll());
        }
        System.out.println("ss");
        System.out.println("ss");
        System.out.println("ss");
        System.out.println("ss");
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
