package com.dot.osore.core.chat.repository;

import com.dot.osore.core.chat.entity.ChattingRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
    List<ChattingRoom> findByNoteIdOrderByCreatedAtDesc(Long noteId);

    void deleteByNoteId(Long noteId);
}
