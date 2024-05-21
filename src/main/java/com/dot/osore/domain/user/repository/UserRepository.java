package com.dot.osore.domain.user.repository;

import com.dot.osore.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
