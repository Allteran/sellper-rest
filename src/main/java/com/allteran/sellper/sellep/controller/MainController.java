package com.allteran.sellper.sellep.controller;

import com.allteran.sellper.sellep.repo.NomenclatureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public String index(Model model) {
        HashMap<Object, Object> data = new HashMap<>();

        data.put("nomList", nomenclatureRepo.findAll());

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
