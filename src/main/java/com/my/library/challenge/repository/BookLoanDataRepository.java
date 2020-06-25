package com.my.library.challenge.repository;

import com.my.library.challenge.entity.BookLoanData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLoanDataRepository extends JpaRepository<BookLoanData, Long> {
}
