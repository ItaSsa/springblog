package com.itainplace.springnblog.repository;

import com.itainplace.springnblog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
