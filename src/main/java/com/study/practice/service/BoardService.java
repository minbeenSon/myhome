package com.study.practice.service;

import com.study.practice.entity.Board;
import com.study.practice.entity.User;
import com.study.practice.repository.BoardRepository;
import com.study.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    //게시물작성
    public void boardWrite(String username, Board board) {
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        boardRepository.save(board);
    }

    //게시물리스트
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    //게시물검색(제목+내용)
    public Page<Board> boardSearchList(Pageable pageable, String title, String content) {
        return boardRepository.findByTitleContainingOrContentContaining(pageable, title, content);
    }

    //게시물검색(제목)
    public Page<Board> boardSearchTitleList(Pageable pageable, String searchKeyword) {
        return boardRepository.findByTitleContaining(pageable, searchKeyword);
    }

    //게시물검색(내용)
    public Page<Board> boardSearchContentList(Pageable pageable, String searchKeyword) {
        return boardRepository.findByContentContaining(pageable, searchKeyword);
    }

    //게시물상세
    public Board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }

    //게시물삭제
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }
}
