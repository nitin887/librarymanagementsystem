package com.librarymanagementsystem.librarymanagementsystem.mapper;

import com.librarymanagementsystem.librarymanagementsystem.dto.PatronDTO;
import com.librarymanagementsystem.librarymanagementsystem.model.Patron;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for converting between {@link Patron} and {@link PatronDTO} objects.
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface PatronMapper {

    /**
     * The singleton instance of the mapper.
     */
    // Remove this line as Spring will manage the instance
    // PatronMapper INSTANCE = Mappers.getMapper(PatronMapper.class);

    /**
     * Converts a {@link Patron} entity to a {@link PatronDTO}.
     *
     * @param patron The {@link Patron} entity to convert.
     * @return The corresponding {@link PatronDTO}.
     */
    PatronDTO toPatronDTO(Patron patron);

    /**
     * Converts a {@link PatronDTO} to a {@link Patron} entity.
     *
     * @param patronDTO The {@link PatronDTO} to convert.
     * @return The corresponding {@link Patron} entity.
     */
    Patron toPatron(PatronDTO patronDTO);
}
