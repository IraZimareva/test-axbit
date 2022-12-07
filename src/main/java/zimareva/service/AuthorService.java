package zimareva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import zimareva.exception.EntityNotFoundException;
import zimareva.model.Author;
import zimareva.model.Book;
import zimareva.repository.AuthorRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookService bookService;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Author.class.getName(), id));
    }

    public List<Author> getAuthors(Map<String, Object> authorParameters) {
        if (authorParameters == null || authorParameters.isEmpty()) {
            return getAllAuthors();
        }
        return getAuthorsByParameters(authorParameters);
    }

    public List<Author> getAllAuthors() {
        return StreamSupport
                .stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Author> getAuthorsByParameters(Map<String, Object> authorParameters) {
        return authorRepository.findAuthorsByParameteres(authorParameters);
    }


    public void deleteAuthor(Long id) {
        Author author = getAuthorById(id);
        authorRepository.delete(author);
    }

    @Transactional
    public Author editAuthor(Long id, Map<String, Object> authorDetails) {
        Author authorToEdit = getAuthorById(id);
        authorDetails.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Author.class, key);
            field.setAccessible(true);
            if (key.equals("dateOfBirth")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateOfBirth = LocalDate.parse((String) value, formatter);
                ReflectionUtils.setField(field, authorToEdit, dateOfBirth);
            } else {
                ReflectionUtils.setField(field, authorToEdit, value);
            }
        });
        return authorToEdit;
    }

    @Transactional
    public Author addBookToAuthor(Long authorId, Long bookId) {
        Author author = getAuthorById(authorId);
        Book book = bookService.getBook(bookId);
        author.addBook(book);
        return author;
    }

    @Transactional
    public Author createBookToAuthor(Long authorId, Book book) {
        Author author = getAuthorById(authorId);
        Book createdBook = bookService.addBook(book);
        author.addBook(createdBook);
        return author;
    }
}
