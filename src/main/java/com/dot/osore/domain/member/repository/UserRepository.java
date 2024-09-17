package com.dot.osore.domain.member.repository;

import com.dot.osore.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO: User -> Member 변경 필요
public interface UserRepository extends JpaRepository<User, Long> {

}
