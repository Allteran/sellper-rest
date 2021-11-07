package com.allteran.sellper.sellep.service;

import com.allteran.sellper.sellep.domain.User;
import com.allteran.sellper.sellep.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByPhone(s);
        if (user == null) {
            throw new UsernameNotFoundException("User with phone number " + s + " not found");
        }
        return user;
    }

    public User findByPhone(String phone) {
        return userRepo.findByPhone(phone);
    }
}
