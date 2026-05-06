package dev.mayur.librarymanagement.core.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Pagination<T> {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean isLast;


    // getters & setters
}

