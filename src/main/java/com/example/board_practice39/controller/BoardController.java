package com.example.board_practice39.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.board_practice39.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    // 목록 (검색 + 페이징)
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "") String searchType,
            @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("boards", boardService.findAll(searchType, keyword, page, 5));
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
        return "board/list";
    }

    // 글쓰기 폼
    @GetMapping("/write")
    public String writeForm() {
        return "board/write";
    }

    // 글쓰기 처리
    @PostMapping("/write")
    public String write(String title, String content, String writer, @RequestParam(required = false) MultipartFile file)
            throws IOException {
        boardService.save(title, content, writer, file);
        return "redirect:/board/list";
    }

    // 상세보기
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("board", boardService.findById(id));
        return "board/detail";
    }
}
