package com.example.board_practice39.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.board_practice39.dto.PageDTO;
import com.example.board_practice39.entity.Board;
import com.example.board_practice39.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileService fileService;

    @Transactional
    public void save(String title, String content, String writer, MultipartFile file) throws IOException {
        String savedName = fileService.upload(file);
        String originalName = (file != null && !file.isEmpty()) ? file.getOriginalFilename() : null;

        Board board = Board.builder()
                .title(title).content(content).writer(writer)
                .fileName(savedName).originalFileName(originalName)
                .build();

        boardRepository.save(board);
    }

    public PageDTO findAll(String searchType, String keyword, int page, int size) {
        List<Board> boards;
        long total;

        if (keyword == null || keyword.isBlank()) {
            boards = boardRepository.findAll(page, size);
            total = boardRepository.count();
        } else {
            boards = boardRepository.search(searchType, keyword.trim(), page, size);
            total = boardRepository.serachCount(searchType, keyword.trim());
        }

        return new PageDTO(boards, page, size, total);
    }

    @Transactional
    public Board findById(Long id) {
        Board board = boardRepository.findById(id);
        if (board == null)
            throw new IllegalArgumentException("게시글이 없습니다. ID = " + id);
        board.increaseViewCount(); // Dirty Checking -> 자동 UPDATE
        return board;
    }
}
