package zimareva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zimareva.exception.EntityNotFoundException;
import zimareva.model.Book;
import zimareva.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    public Book getBook(Long id){
        return bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Book.class.getName(), id));
    }

    public List<Book> getBooks(){
        return StreamSupport
                .stream(bookRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    public void deleteBook(Long id){
        Book book = getBook(id);
        bookRepository.delete(book);
    }

    @Transactional
    public Book editBook(Long id, Book book){
        Book bookToEdit = getBook(id);
        bookToEdit.setIsbn(book.getIsbn());
        bookToEdit.setGenre(book.getGenre());
        return bookToEdit;
    }
}
