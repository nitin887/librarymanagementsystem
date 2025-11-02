package com.librarymanagementsystem.librarymanagementsystem.service.impl;

import com.librarymanagementsystem.librarymanagementsystem.dto.BorrowingRecordDTO;
import com.librarymanagementsystem.librarymanagementsystem.exception.ResourceNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.mapper.BorrowingRecordMapper;
import com.librarymanagementsystem.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.librarymanagementsystem.model.BorrowingRecord;
import com.librarymanagementsystem.librarymanagementsystem.model.Patron;
import com.librarymanagementsystem.librarymanagementsystem.repository.BookRepository;
import com.librarymanagementsystem.librarymanagementsystem.repository.BorrowingRecordRepository;
import com.librarymanagementsystem.librarymanagementsystem.repository.PatronRepository;
import com.librarymanagementsystem.librarymanagementsystem.service.BorrowingRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final BorrowingRecordMapper borrowingRecordMapper;

    public BorrowingRecordServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, PatronRepository patronRepository, BorrowingRecordMapper borrowingRecordMapper) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.borrowingRecordMapper = borrowingRecordMapper;
    }

    @Override
    public List<BorrowingRecordDTO> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll().stream()
                .map(borrowingRecordMapper::toBorrowingRecordDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BorrowingRecordDTO getBorrowingRecordById(Long id) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found with id: " + id));
        return borrowingRecordMapper.toBorrowingRecordDTO(borrowingRecord);
    }

    @Override
    public BorrowingRecordDTO borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + patronId));

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setDueDate(LocalDate.now().plusWeeks(2)); // Due in 2 weeks

        return borrowingRecordMapper.toBorrowingRecordDTO(borrowingRecordRepository.save(borrowingRecord));
    }

    @Override
    public BorrowingRecordDTO returnBook(Long borrowingRecordId) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findById(borrowingRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found with id: " + borrowingRecordId));

        borrowingRecord.setReturnDate(LocalDate.now());
        return borrowingRecordMapper.toBorrowingRecordDTO(borrowingRecordRepository.save(borrowingRecord));
    }

    @Override
    public List<BorrowingRecordDTO> getBorrowingRecordsByBook(Long bookId) {
        return borrowingRecordRepository.findByBookId(bookId).stream()
                .map(borrowingRecordMapper::toBorrowingRecordDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingRecordDTO> getBorrowingRecordsByPatron(Long patronId) {
        return borrowingRecordRepository.findByPatronId(patronId).stream()
                .map(borrowingRecordMapper::toBorrowingRecordDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingRecordDTO> getOverdueBooks() {
        return borrowingRecordRepository.findByDueDateBeforeAndReturnDateIsNull(LocalDate.now()).stream()
                .map(borrowingRecordMapper::toBorrowingRecordDTO)
                .collect(Collectors.toList());
    }
}
