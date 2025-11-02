package com.librarymanagementsystem.librarymanagementsystem.repository;

import com.librarymanagementsystem.librarymanagementsystem.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatronRepository extends JpaRepository<Patron, Long> {

    List<Patron> findByNameContaining(String name);
}
