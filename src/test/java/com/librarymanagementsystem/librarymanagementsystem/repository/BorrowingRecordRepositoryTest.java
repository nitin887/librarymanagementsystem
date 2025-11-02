package com.librarymanagementsystem.librarymanagementsystem.repository;

import com.librarymanagementsystem.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.librarymanagementsystem.model.BorrowingRecord;
import com.librarymanagementsystem.librarymanagementsystem.model.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class BorrowingRecordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    private Book book;
    private Patron patron;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublishedDate(LocalDate.of(2022, 1, 1));
        book.setIsbn("1234567890");
        entityManager.persist(book);

        patron = new Patron();
        patron.setName("Test Patron");
        entityManager.persist(patron);
    }

    @Test
    public void whenFindByBookId_thenReturnBorrowingRecord() {
        // given
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        entityManager.persist(borrowingRecord);
        entityManager.flush();

        // when
        List<BorrowingRecord> found = borrowingRecordRepository.findByBookId(book.getId());

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getBook().getId()).isEqualTo(book.getId());
    }

    @Test
    public void whenFindByPatronId_thenReturnBorrowingRecord() {
        // given
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        entityManager.persist(borrowingRecord);
        entityManager.flush();

        // when
        List<BorrowingRecord> found = borrowingRecordRepository.findByPatronId(patron.getId());

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getPatron().getId()).isEqualTo(patron.getId());
    }

    @Test
    public void whenFindByDueDateBeforeAndReturnDateIsNull_thenReturnBorrowingRecord() {
        // given
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now().minusDays(10));
        borrowingRecord.setDueDate(LocalDate.now().minusDays(1));
        entityManager.persist(borrowingRecord);
        entityManager.flush();

        // when
        List<BorrowingRecord> found = borrowingRecordRepository.findByDueDateBeforeAndReturnDateIsNull(LocalDate.now());

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getDueDate()).isBefore(LocalDate.now());
        assertThat(found.get(0).getReturnDate()).isNull();
    }
}
