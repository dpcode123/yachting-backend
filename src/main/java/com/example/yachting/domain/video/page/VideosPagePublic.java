package com.example.yachting.domain.video.page;

import org.springframework.data.domain.Sort;

/**
 * Videos Pageable/Page settings - PUBLIC page.
 * @author dp
 */
public class VideosPagePublic {
    private String pageNumber = "0";
    private int pageSize = 36;
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private String sortBy = "id";

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
