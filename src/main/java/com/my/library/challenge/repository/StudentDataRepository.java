package com.my.library.challenge.repository;

import com.my.library.challenge.entity.StudentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDataRepository extends JpaRepository<StudentData, Long> {
    StudentData findByName(String name);
}
