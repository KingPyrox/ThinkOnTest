package com.ThinkOn.Test.Repositories;

import com.ThinkOn.Test.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
