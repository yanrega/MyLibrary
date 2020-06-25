package com.my.library.challenge.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "book_data")
@Data
public class BookData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String publisher;

    @Column(name = "publication_year")
    private int publicationYear;

    private String author;

    private int total;
}
