package zimareva.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zimareva.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    List<Book> findByGenreId(Long id);

    @Query(value = "SELECT * FROM book WHERE author_id = :authorId ",
            nativeQuery = true)
    List<Book> findByAuthorId(@Param("authorId") Long id);
}
