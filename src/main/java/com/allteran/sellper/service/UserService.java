package com.allteran.sellper.service;

import com.allteran.sellper.domain.Role;
import com.allteran.sellper.domain.User;
import com.allteran.sellper.exception.IncorrectPasswordException;
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
import java.util.HashSet;
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
        user.setCreationDate(LocalDateTime.now());
//        Set<Role> roles = new HashSet<>();
//        roles.add(Role.USER);
//        roles.add(Role.MANAGER);
//        roles.add(Role.ADMIN);
//
//        user.setRoles(roles);
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setDealerId(Const.DEFAULT_DEALER_ID);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);
        return true;
    }

    public User updateUser(User userFromDb, User user) {
        boolean isPasswordsMatches = passwordEncoder.matches(user.getPassword(), userFromDb.getPassword());
        if(user.getNewPassword() != null) {
            if(isPasswordsMatches || user.getPasswordConfirm() == null) {
                userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                throw new IncorrectPasswordException();
            }
        }

        BeanUtils.copyProperties(user, userFromDb,"id", "password", "newPassword", "passwordConfirm");
        return userRepo.save(userFromDb);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
}
