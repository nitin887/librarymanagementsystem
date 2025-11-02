package com.librarymanagementsystem.librarymanagementsystem.repository;

import com.librarymanagementsystem.librarymanagementsystem.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void whenFindByNameContaining_thenReturnAuthor() {
        // given
        Author author = new Author();
        author.setName("Test Author");
        authorRepository.save(author);

        // when
        List<Author> found = authorRepository.findByNameContaining("Test");

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getName()).isEqualTo("Test Author");
    }
}
