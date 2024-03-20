package com.facenet.bt2.service;

import com.facenet.bt2.dto.LibraryDto;
import com.facenet.bt2.entity.Library;
import com.facenet.bt2.request.AddLibraryWithBookRequest;
import com.facenet.bt2.request.LibraryRequest;

import java.util.List;
import java.util.Set;

public interface ILibraryService {
    void addLibrary(LibraryRequest libraryRequest);
    void updateLibrary(int id, LibraryRequest libraryRequest);
    void deleteLibrary(int id);
    Set<LibraryDto> getAllLibrary();
    LibraryDto getLibraryById(int id);
    LibraryDto convertToDto(Library library, IBookService bookService);

    void addLibraryWithBook(AddLibraryWithBookRequest addLibraryWithBookRequest);
}
