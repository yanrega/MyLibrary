package com.my.library.challenge.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookLoanDataVO {
    @JsonProperty("student_name")
    private String studentName;

    @JsonProperty("book_title")
    private String bookTitle;
}
