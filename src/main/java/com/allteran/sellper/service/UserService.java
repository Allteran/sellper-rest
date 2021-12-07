package com.allteran.sellper.service;

import com.allteran.sellper.domain.Role;
import com.allteran.sellper.domain.User;
import com.allteran.sellper.repo.UserRepo;
import com.allteran.sellper.util.Const;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public boolean addUser(User user) {
        User userFromDb = findByPhone(user.getPhone());
        if (userFromDb != null) {
            return false;
        }
        user.setLastVisit(LocalDateTime.now());
        user.setRoles(Collections.singleton(Role.USER));
        user.setDealerId(Const.DEFAULT_DEALER_ID);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);
        return true;
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public User updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        return updateUser(user);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
}
