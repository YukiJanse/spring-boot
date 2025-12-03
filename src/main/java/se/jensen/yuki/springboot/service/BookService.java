package se.jensen.yuki.springboot.service;

import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.DTO.BookRequestDTO;
import se.jensen.yuki.springboot.DTO.BookResponseDTO;
import se.jensen.yuki.springboot.model.Book;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public BookResponseDTO bookToResponseDTO(Book book) {
        return new BookResponseDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getYear());
    }

    public Book requestDTOToBook(BookRequestDTO requestDTO) {
        return new Book(0L, requestDTO.title(), requestDTO.author(), requestDTO.year());
    }
}
