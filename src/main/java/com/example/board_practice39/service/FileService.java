package com.example.board_practice39.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String upload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty())
            return null;

        // 확장자 추출 : "photo.jpg" -> ".jpg"
        String original = file.getOriginalFilename();
        String ext = original.substring(original.lastIndexOf("."));

        // UUID로 유일한 파일명 생성
        String saved = UUID.randomUUID() + ext;

        // 폴더 없으면 생성
        File dir = new File(uploadDir);
        if (!dir.exists())
            dir.mkdirs();

        // 파일 저장
        file.transferTo(new File(dir, saved));
        return saved;
    }
}
