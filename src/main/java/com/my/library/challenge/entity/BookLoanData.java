package com.my.library.challenge.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "book_loan_data")
@Data
public class BookLoanData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_date")
    private Date loanDate;

    @Column(name = "loan_limit_date")
    private Date loanLimitDate;

    @Column(name = "return_date")
    private Date returnDate;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "book_data_id", nullable = false )
    private BookData bookData;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "student_data_id", nullable = false )
    private StudentData studentData;
}
