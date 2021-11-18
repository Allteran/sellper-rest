package com.allteran.sellper.repo;

import com.allteran.sellper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByPhone(String phone);
}
