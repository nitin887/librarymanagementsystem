package com.librarymanagementsystem.librarymanagementsystem.repository;

import com.librarymanagementsystem.librarymanagementsystem.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void whenFindByTitleContaining_thenReturnBook() {
        // given
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublishedDate(LocalDate.of(2022, 1, 1));
        book.setIsbn("1234567890");
        bookRepository.save(book);

        // when
        List<Book> found = bookRepository.findByTitleContaining("Test");

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getTitle()).isEqualTo("Test Book");
    }
}
