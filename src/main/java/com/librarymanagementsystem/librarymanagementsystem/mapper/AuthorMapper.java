package com.librarymanagementsystem.librarymanagementsystem.mapper;

import com.librarymanagementsystem.librarymanagementsystem.dto.AuthorDTO;
import com.librarymanagementsystem.librarymanagementsystem.model.Author;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between {@link Author} and {@link AuthorDTO} objects.
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {

    /**
     * Converts an {@link Author} entity to an {@link AuthorDTO}.
     *
     * @param author The {@link Author} entity to convert.
     * @return The corresponding {@link AuthorDTO}.
     */
    AuthorDTO toAuthorDTO(Author author);

    /**
     * Converts an {@link AuthorDTO} to an {@link Author} entity.
     *
     * @param authorDTO The {@link AuthorDTO} to convert.
     * @return The corresponding {@link Author} entity.
     */
    Author toAuthor(AuthorDTO authorDTO);
}
