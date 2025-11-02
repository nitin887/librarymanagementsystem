package com.librarymanagementsystem.librarymanagementsystem.repository;

import com.librarymanagementsystem.librarymanagementsystem.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    List<BorrowingRecord> findByBookId(Long bookId);

    List<BorrowingRecord> findByPatronId(Long patronId);

    List<BorrowingRecord> findByDueDateBeforeAndReturnDateIsNull(LocalDate date);
}
