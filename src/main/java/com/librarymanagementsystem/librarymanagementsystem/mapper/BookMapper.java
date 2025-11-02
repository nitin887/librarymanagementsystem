package com.librarymanagementsystem.librarymanagementsystem.mapper;

import com.librarymanagementsystem.librarymanagementsystem.dto.BookDTO;
import com.librarymanagementsystem.librarymanagementsystem.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for converting between {@link Book} and {@link BookDTO} objects.
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    /**
     * The singleton instance of the mapper.
     */
    // Remove this line as Spring will manage the instance
    // BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    /**
     * Converts a {@link Book} entity to a {@link BookDTO}.
     *
     * @param book The {@link Book} entity to convert.
     * @return The corresponding {@link BookDTO}.
     */
    BookDTO toBookDTO(Book book);

    /**
     * Converts a {@link BookDTO} to a {@link Book} entity.
     *
     * @param bookDTO The {@link BookDTO} to convert.
     * @return The corresponding {@link Book} entity.
     */
    Book toBook(BookDTO bookDTO);
}
