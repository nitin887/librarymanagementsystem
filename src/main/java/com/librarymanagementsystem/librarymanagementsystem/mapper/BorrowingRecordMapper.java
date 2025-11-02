package com.librarymanagementsystem.librarymanagementsystem.mapper;

import com.librarymanagementsystem.librarymanagementsystem.dto.BorrowingRecordDTO;
import com.librarymanagementsystem.librarymanagementsystem.model.BorrowingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for converting between {@link BorrowingRecord} and {@link BorrowingRecordDTO} objects.
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface BorrowingRecordMapper {

    /**
     * The singleton instance of the mapper.
     */
    // Remove this line as Spring will manage the instance
    // BorrowingRecordMapper INSTANCE = Mappers.getMapper(BorrowingRecordMapper.class);

    /**
     * Converts a {@link BorrowingRecord} entity to a {@link BorrowingRecordDTO}.
     *
     * @param borrowingRecord The {@link BorrowingRecord} entity to convert.
     * @return The corresponding {@link BorrowingRecordDTO}.
     */
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "patron.id", target = "patronId")
    BorrowingRecordDTO toBorrowingRecordDTO(BorrowingRecord borrowingRecord);

    /**
     * Converts a {@link BorrowingRecordDTO} to a {@link BorrowingRecord} entity.
     * The 'book' and 'patron' fields are ignored because they are typically set manually
     * in the service layer after fetching them from the database.
     *
     * @param borrowingRecordDTO The {@link BorrowingRecordDTO} to convert.
     * @return The corresponding {@link BorrowingRecord} entity.
     */
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "patron", ignore = true)
    BorrowingRecord toBorrowingRecord(BorrowingRecordDTO borrowingRecordDTO);
}
