package zimareva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zimareva.exception.EntityNotFoundException;
import zimareva.model.Author;
import zimareva.model.Book;
import zimareva.repository.AuthorRepository;

import javax.transaction.Transactional;
import java.util.List;
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

    public Author getAuthor(Long id) {
        return authorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Author.class.getName(), id));
    }

    public List<Author> getAuthors() {
        return StreamSupport
                .stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void deleteAuthor(Long id) {
        Author author = getAuthor(id);
        authorRepository.delete(author);
    }

    @Transactional
    public Author editAuthor(Long id, Author author) {
        Author authorToEdit = getAuthor(id);
        authorToEdit.setLastname(author.getLastname());
        authorToEdit.setFirstname(author.getFirstname());
        authorToEdit.setMiddlename(author.getMiddlename());
        authorToEdit.setDateOfBirth(author.getDateOfBirth());
        //Надо ли?
        authorToEdit.setBooks(author.getBooks());
        return authorToEdit;
    }

    @Transactional
    public Author addBookToAuthor(Long authorId, Long bookId) {
        Author author = getAuthor(authorId);
        Book book = bookService.getBook(bookId);
        author.addBook(book);
        return author;
    }

    @Transactional
    public Author removeBookFromAuthor(Long authorId, Long bookId) {
        Author author = getAuthor(authorId);
        Book book = bookService.getBook(bookId);
        author.deleteBook(book);
        return author;
    }
}
