package com.librarymanagementsystem.librarymanagementsystem.service;

import com.librarymanagementsystem.librarymanagementsystem.dto.BorrowingRecordDTO;

import java.util.List;

public interface BorrowingRecordService {

    List<BorrowingRecordDTO> getAllBorrowingRecords();

    BorrowingRecordDTO getBorrowingRecordById(Long id);

    BorrowingRecordDTO borrowBook(Long bookId, Long patronId);

    BorrowingRecordDTO returnBook(Long borrowingRecordId);

    List<BorrowingRecordDTO> getBorrowingRecordsByBook(Long bookId);

    List<BorrowingRecordDTO> getBorrowingRecordsByPatron(Long patronId);

    List<BorrowingRecordDTO> getOverdueBooks();
}
