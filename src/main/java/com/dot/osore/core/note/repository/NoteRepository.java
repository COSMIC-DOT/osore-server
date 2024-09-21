package com.dot.osore.core.note.repository;

import com.dot.osore.core.note.entity.Note;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByMember_Id(Long id);
}
