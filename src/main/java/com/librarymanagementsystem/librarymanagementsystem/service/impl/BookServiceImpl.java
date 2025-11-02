package com.librarymanagementsystem.librarymanagementsystem.service.impl;

import com.librarymanagementsystem.librarymanagementsystem.dto.BookDTO;
import com.librarymanagementsystem.librarymanagementsystem.exception.ResourceNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.mapper.BookMapper;
import com.librarymanagementsystem.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.librarymanagementsystem.repository.BookRepository;
import com.librarymanagementsystem.librarymanagementsystem.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return bookMapper.toBookDTO(book);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookMapper.toBook(bookDTO);
        return bookMapper.toBookDTO(bookRepository.save(book));
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setPublishedDate(bookDTO.getPublishedDate());

        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toBookDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title).stream()
                .map(bookMapper::toBookDTO)
                .collect(Collectors.toList());
    }
}
