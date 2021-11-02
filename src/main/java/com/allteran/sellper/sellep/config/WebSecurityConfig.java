package com.allteran.sellper.sellep.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    //    @Bean
//    public PrincipalExtractor principalExtractor(UserDetailsRepo userDetailsRepo) {
//        return map -> {
//            String id = (String) map.get("sub"); //as id marked in google account service
//             User user = userDetailsRepo.findById(id).orElseGet(() -> {
//                User newUser = new User();
//                newUser.setId(id);
//                newUser.setEmail((String) map.get("email"));
//                newUser.setName((String) map.get("name"));
//                newUser.setGender((String) map.get("gender"));
//                newUser.setUserpic((String) map.get("picture"));
//                newUser.setLocale((String) map.get("locale"));
//
//                return newUser;
//            });
//             user.setLastVisit(LocalDateTime.now());
//
//             return userDetailsRepo.save(user);
//        };
//    }
}
