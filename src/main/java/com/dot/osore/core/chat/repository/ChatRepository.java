package com.dot.osore.core.chat.repository;

import com.dot.osore.core.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    void deleteByChattingRoomId(Long id);
}
