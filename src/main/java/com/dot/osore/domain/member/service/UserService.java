package com.dot.osore.domain.member.service;

import com.dot.osore.domain.member.dto.UserResponse;
import com.dot.osore.domain.member.entity.User;
import com.dot.osore.domain.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// TODO: User -> Member 변경 필요
@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;

    public Long save(String name, String avatar) {
        User savedUser = userRepository.save(User.builder().name(name).avatar(avatar).build());
        return savedUser.getId();
    }

    public UserResponse findUser(Long id) throws Exception {
        User user = this.findById(id);
        return UserResponse.builder().user(user).build();
    }

    private User findById(Long id) throws Exception {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * 사용자 이름으로 사용자를 찾는 메서드
     *
     * @param name 사용자 이름
     */
    public User findByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }
}
