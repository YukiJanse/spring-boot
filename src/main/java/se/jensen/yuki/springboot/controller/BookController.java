package se.jensen.yuki.springboot.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.DTO.BookRequestDTO;
import se.jensen.yuki.springboot.DTO.BookResponseDTO;
import se.jensen.yuki.springboot.model.Book;
import se.jensen.yuki.springboot.service.BookService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService service;
    private final List<Book> books = new ArrayList<>();

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity
                .ok()
                .body(books
                        .stream()
                        .map(service::bookToResponseDTO)
                        .toList()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable int id) {
        if (id < 0 || id > books.size()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok()
                .body(service.bookToResponseDTO(books.get(id)));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO bookDTO) {
        Book book = service.requestDTOToBook(bookDTO);
        books.add(book);
        return ResponseEntity
                .ok()
                .body(service.bookToResponseDTO(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable int id, @Valid @RequestBody BookRequestDTO bookDTO) {
        Book book = service.requestDTOToBook(bookDTO);
        if (id < 0 || id > books.size()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(service.bookToResponseDTO(books.set(id, book)));
    }
}
