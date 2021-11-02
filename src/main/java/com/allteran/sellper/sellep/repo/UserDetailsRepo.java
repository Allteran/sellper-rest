package com.allteran.sellper.sellep.repo;

import com.allteran.sellper.sellep.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User,String> {
}
