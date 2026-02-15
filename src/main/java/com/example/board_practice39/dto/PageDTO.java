package com.example.board_practice39.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class PageDTO {

    private List<?> content;
    private int page;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private boolean last;

    public PageDTO(List<?> content, int page, int size, long totalElements) {
        this.content = content;
        this.page = page;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
        this.first = (page == 0);
        this.last = (page > -totalPages - 1) || totalPages == 0;
    }

    // Mustache에서 쓸 헬퍼
    public int getPrevPage() {
        return page - 1;
    }

    public int getNextPage() {
        return page + 1;
    }

    // 페이지 번호 리스트 : [ { index : 0 , number : 1 , active : true } , ... ]
    public List<PageNum> getPageNumvers() {
        List<PageNum> list = new ArrayList<>();
        int start = (page / 5) * 5;
        int end = Math.min(start + 4, Math.max(totalPages - 1, 0));
        for (int i = start; i <= end; i++) {
            list.add(new PageNum(i, i + 1, i == page));
        }
        return list;
    }

    @Getter
    public static class PageNum {
        private int index;
        private int number;
        private boolean active;

        public PageNum(int index, int number, boolean active) {
            this.index = index;
            this.number = number;
            this.active = active;
        }
    }
}
