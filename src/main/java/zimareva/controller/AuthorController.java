package zimareva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zimareva.model.Author;
import zimareva.model.Book;
import zimareva.service.AuthorService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAuthors(@RequestParam(required = false) Map<String, Object> authorParameters) {
        List<Author> authors = authorService.getAuthors(authorParameters);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable(value = "id") Long authorId) {
        Author author = authorService.getAuthorById(authorId);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author createdAuthor = authorService.addAuthor(author);
        return new ResponseEntity<>(createdAuthor, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public void deleteAuthor(@PathVariable(value = "id") Long authorId) {
        authorService.deleteAuthor(authorId);
    }

    @PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> partialUpdateGeneric(@PathVariable("id") Long authorId,
                                                       @RequestBody Map<String, Object> authorDetails) {
        Author editedAuthor = authorService.editAuthor(authorId, authorDetails);
        return new ResponseEntity<>(editedAuthor, HttpStatus.OK);
    }

    //todo: возможно стоит подумать над URL
    @PutMapping(value = "{idAuthor}/books/{idBook}")
    public ResponseEntity<Author> addBookToAuthor(@PathVariable(value = "idAuthor") Long idAuthor,
                                                  @PathVariable(value = "idBook") Long idBook) {
        Author author = authorService.addBookToAuthor(idAuthor, idBook);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    //todo: возможно стоит подумать над URL
    @PostMapping(value = "{idAuthor}/books")
    public ResponseEntity<Author> createBookToAuthor(@PathVariable(value = "idAuthor") Long idAuthor,
                                                     @RequestBody Book book) {
        Author author = authorService.createBookToAuthor(idAuthor, book);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }
}
