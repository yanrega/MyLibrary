package com.my.library.challenge.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookDataVO {
    @JsonProperty("title")
    private String title;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("publication_year")
    private int publicationYear;

    @JsonProperty("author")
    private String author;

    @JsonProperty("total")
    private int total;
}
