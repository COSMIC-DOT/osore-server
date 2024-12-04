package com.dot.osore.core.memo.repository;

import com.dot.osore.core.memo.entity.Memo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findByNoteIdOrderById(Long noteId);

    void deleteByNoteId(Long noteId);
}
