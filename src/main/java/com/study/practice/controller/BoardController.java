package com.study.practice.controller;

import com.study.practice.entity.Board;
import com.study.practice.repository.BoardRepository;
import com.study.practice.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/list")
    public String boardList(Model model,
                            @PageableDefault(size = 4, page = 0, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
                            String searchKeyword, String searchType) {
        Page<Board> list = null;

        if(searchKeyword == null || searchKeyword == "") {
            list = boardService.boardList(pageable);
        } else {
            if(searchType.equals("제목내용")) {
                list = boardService.boardSearchList(pageable, searchKeyword, searchKeyword);
            } else if(searchType.equals("제목")) {
                list = boardService.boardSearchTitleList(pageable,searchKeyword);
            } else if(searchType.equals("내용")) {
                list = boardService.boardSearchContentList(pageable, searchKeyword);
            }
        }
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(1, nowPage -4);
        int endPage = Math.min(list.getTotalPages(), nowPage + 4);
        model.addAttribute("boards", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/list";
    }

    @GetMapping("/form")
    public String boardWrite() {
        return "board/form";
    }

    @PostMapping("/form/process")
    public String boardWriteProcess(Board board, Model model) {
        boardService.boardWrite(board);

        model.addAttribute("message", "게시물작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
    }

    @GetMapping("/view")
    public String boardView(Model model, Integer id) {
        model.addAttribute("board", boardService.boardView(id));
        return "board/view";
    }

    @GetMapping("/delete")
    public String boardDelete(Integer id, Model model) {
        boardService.boardDelete(id);
        model.addAttribute("message", "게시물삭제가 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
    }

    @GetMapping("/modify")
    public String boardModify(Model model, Integer id) {
        model.addAttribute("board",boardService.boardView(id));
        return "board/modify";
    }

    @PostMapping("/modify/process")
    public String boardModifyProcess(Model model, Integer id, Board board) {
        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.boardWrite(boardTemp);

        model.addAttribute("message", "게시물수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/view?id=" + id);
        return "message";
    }
}
