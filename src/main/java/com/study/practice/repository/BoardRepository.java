package com.study.practice.repository;

import com.study.practice.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findByTitleContainingOrContentContaining(Pageable pageable, String title, String content);
    Page<Board> findByTitleContaining(Pageable pageable, String searchKeyword);
    Page<Board> findByContentContaining(Pageable pageable, String searchKeyword);
}
