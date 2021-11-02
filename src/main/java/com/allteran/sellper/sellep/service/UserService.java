package com.allteran.sellper.sellep.service;

import com.allteran.sellper.sellep.domain.User;
import com.allteran.sellper.sellep.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserDetailsService {
    @Autowired
    private UserDetailsRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = userRepo.findById(s);
        return null;
    }
}
