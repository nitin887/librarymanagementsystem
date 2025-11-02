package com.librarymanagementsystem.librarymanagementsystem.repository;

import com.librarymanagementsystem.librarymanagementsystem.model.Patron;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class PatronRepositoryTest {

    @Autowired
    private PatronRepository patronRepository;

    @Test
    public void whenFindByNameContaining_thenReturnPatron() {
        // given
        Patron patron = new Patron();
        patron.setName("Test Patron");
        patronRepository.save(patron);

        // when
        List<Patron> found = patronRepository.findByNameContaining("Test");

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getName()).isEqualTo("Test Patron");
    }
}
