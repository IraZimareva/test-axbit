package zimareva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zimareva.exception.EntityNotFoundException;
import zimareva.model.Book;
import zimareva.model.Genre;
import zimareva.repository.GenreRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookService bookService;

    @Autowired
    public GenreService(GenreRepository genreRepository, BookService bookService) {
        this.genreRepository = genreRepository;
        this.bookService = bookService;
    }

    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre getGenre(Long id) {
        return genreRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Genre.class.getName(), id));
    }

    public List<Genre> getGenres() {
        return StreamSupport
                .stream(genreRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void deleteGenre(Long id) {
        Genre genre = getGenre(id);
        genreRepository.delete(genre);
    }

    @Transactional
    public Genre editGenre(Long id, Genre genre) {
        Genre genreToEdit = getGenre(id);
        genreToEdit.setName(genre.getName());
        //Надо ли?
        genreToEdit.setBooks(genre.getBooks());
        return genreToEdit;
    }

    @Transactional
    public Genre addBookToGenre(Long genreId, Long bookId) {
        Genre genre = getGenre(genreId);
        Book book = bookService.getBook(bookId);
        genre.addBook(book);
        book.setGenre(genre);
        return genre;
    }

    @Transactional
    public Genre removeBookFromGenre(Long genreId, Long bookId) {
        Genre genre = getGenre(genreId);
        Book book = bookService.getBook(bookId);
        genre.deleteBook(book);
        return genre;
    }
}
