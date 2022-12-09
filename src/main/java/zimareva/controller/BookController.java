package zimareva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zimareva.model.Author;
import zimareva.model.Book;
import zimareva.model.dto.BookDTO;
import zimareva.service.BookService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) Map<String, Object> bookParameters) {
        List<Book> books = bookService.getBooks(bookParameters);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Book> getBook(@PathVariable(value = "id") Long bookId) {
        Book book = bookService.getBook(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDto) {
        Book createdBook = bookService.addBook(bookDto);
        return new ResponseEntity<>(createdBook, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public void deleteBook(@PathVariable(value = "id") Long bookId) {
        bookService.deleteBook(bookId);
    }

    @PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> partialUpdateBook(@PathVariable("id") Long bookId,
                                                  @RequestBody Map<String, Object> bookDetails) {
        Book editedBook = bookService.editBook(bookId, bookDetails);
        return new ResponseEntity<>(editedBook, HttpStatus.OK);
    }

    @PutMapping(value = "{idBook}")
    public ResponseEntity<Author> addBookToAuthor(@PathVariable(value = "idBook") Long idBook,
                                                  @RequestParam(name = "author_id") Long idAuthor) {
        Author author = bookService.addBookToAuthor(idAuthor, idBook);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Author> createBookToAuthor(@RequestParam(value = "author_id") Long idAuthor,
                                                     @RequestBody BookDTO bookDto) {
        Author author = bookService.createBookToAuthor(idAuthor, bookDto);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }
}
