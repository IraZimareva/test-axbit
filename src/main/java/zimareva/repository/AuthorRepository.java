package zimareva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zimareva.model.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>, AuthorRepositoryCustom{
}
