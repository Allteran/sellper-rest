package com.allteran.sellper.controller;

import com.allteran.sellper.domain.Role;
import com.allteran.sellper.domain.User;
import com.allteran.sellper.repo.NomenclatureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    @Value("${spring.profiles.active:prod}")
    private String profile;
    @Value("${repair.status.unrepaired.id}")
    private int REPAIR_STATUS_UNREPAIRED;
    @Value("${repair.status.paid.id}")
    private int REPAIR_STATUS_PAID;
    @Value("${repair.status.repaired.id}")
    private int REPAIR_STATUS_REPAIRED;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        roles.add(Role.ADMIN);
        roles.add(Role.MANAGER);

        if (user != null) {
            data.put("profile", user);
            data.put("roles", roles);
            data.put("statusIdPaid", REPAIR_STATUS_PAID);
            data.put("statusIdUnrepaired", REPAIR_STATUS_UNREPAIRED);
            data.put("statusIdRepaired", REPAIR_STATUS_REPAIRED);


        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
