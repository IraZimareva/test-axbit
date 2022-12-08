package zimareva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import zimareva.exception.EntityNotFoundException;
import zimareva.model.Book;
import zimareva.model.Genre;
import zimareva.model.dto.BookDTO;
import zimareva.repository.BookRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final GenreService genreService;

    @Autowired
    public BookService(BookRepository bookRepository, GenreService genreService) {
        this.bookRepository = bookRepository;
        this.genreService = genreService;
    }

    public Book addBook(BookDTO bookDto) {
        Genre genre = genreService.getGenre(bookDto.getGenreId());
        return bookRepository.save(Book.from(bookDto, genre));
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Book.class.getName(), id));
    }

    public List<Book> getBooks(Map<String, Object> bookParameters) {
        if (bookParameters == null || bookParameters.isEmpty()) {
            return getAllBooks();
        }
        return getBooksByParameters(bookParameters);
    }

    public List<Book> getAllBooks() {
        return StreamSupport
                .stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByParameters(Map<String, Object> bookParameters) {
        return bookRepository.findBooksByParameteres(bookParameters);
    }

    public void deleteBook(Long id) {
        Book book = getBook(id);
        bookRepository.delete(book);
    }

    @Transactional
    public Book editBook(Long id, Map<String, Object> bookDetails) {
        Book bookToEdit = getBook(id);
        bookDetails.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Book.class, key);
            field.setAccessible(true);
            if (key.equals("genre")) {
                Genre genre = genreService.getGenre(Long.valueOf((Integer)value));
                ReflectionUtils.setField(field, bookToEdit, genre);
            } else {
                ReflectionUtils.setField(field, bookToEdit, value);
            }
        });
        return bookToEdit;
    }
}
