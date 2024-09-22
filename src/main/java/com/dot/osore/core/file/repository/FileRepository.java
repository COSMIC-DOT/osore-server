package com.dot.osore.core.file.repository;

import com.dot.osore.core.file.entity.File;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByNote_Id(Long id);
}
