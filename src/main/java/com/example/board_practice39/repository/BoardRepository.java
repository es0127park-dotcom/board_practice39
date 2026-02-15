package com.example.board_practice39.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.board_practice39.entity.Board;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    // 전체 목록 (페이징)
    public List<Board> findAll(int page, int size) {
        return em.createQuery("SELECT b FROM Board b ORDER BY b.id DESC", Board.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    public long count() {
        return em.createQuery("SELECT COUNT(b) FROM Board b", Long.class)
                .getSingleResult();
    }

    // 검색 (페이징)
    public List<Board> search(String type, String keyword, int page, int size) {
        String where = getWhere(type);
        return em.createQuery("SELECT b FROM Board b WHERE " + where + " ORDER BY b.id DESC", Board.class)
                .setParameter("kw", "%" + keyword + "%")
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    public Long serachCount(String type, String keyword) {
        String where = getWhere(type);
        return em.createQuery("SELECT COUNT(b) FROM Board b WHERE " + where, Long.class)
                .setParameter("kw", "%" + keyword + "%")
                .getSingleResult();
    }

    private String getWhere(String type) {
        return switch (type) {
            case "writer" -> "LOWER(b.writer) LIKE LOWER(:kw)";
            case "titleContent" -> "LOWER(b.title) LIKE LOWER(:kw) OR LOWER(b.content) LIKE LOWER(:kw)";
            default -> "LOWER(b.title) LIKE LOWER(:kw)";
        };
    }
}
