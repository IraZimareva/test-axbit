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

    @Query("SELECT e FROM book e WHERE e.authorId = :authorId ")
    List<Book> findByAuthorId(@Param("authorId") Long id);
}
