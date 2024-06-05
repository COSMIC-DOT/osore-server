package com.dot.osore.domain.note.repository;

import com.dot.osore.domain.note.entity.Note;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUser_UserId(Long id);
}
