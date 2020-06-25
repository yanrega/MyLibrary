package com.my.library.challenge.repository;

import com.my.library.challenge.entity.BookData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDataRepository extends JpaRepository<BookData, Long> {
    BookData findByTitle(String title);
}
