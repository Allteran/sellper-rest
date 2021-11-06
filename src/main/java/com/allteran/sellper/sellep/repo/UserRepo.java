package com.allteran.sellper.sellep.repo;

import com.allteran.sellper.sellep.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByPhone(String phone);
}
