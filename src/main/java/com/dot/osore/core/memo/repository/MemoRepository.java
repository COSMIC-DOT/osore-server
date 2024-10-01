package com.dot.osore.core.memo.repository;

import com.dot.osore.core.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
}
