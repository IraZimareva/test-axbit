package zimareva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zimareva.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>, BookRepositoryCustom {
}
