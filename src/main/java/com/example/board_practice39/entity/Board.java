package com.example.board_practice39.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "board_tb")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false, length = 50)
    private String writer;

    @Column(name = "file_name")
    private String fileName; // 서버 저장용(UUID)

    @Column(name = "original_file_name")
    private String originalFileName; // 원본 파일명

    @Column(name = "view_count")
    private int viewCount;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public Board(String title, String content, String writer, String fileName, String originalFileName) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}