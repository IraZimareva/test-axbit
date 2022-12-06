package zimareva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zimareva.model.Author;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findByLastname(String lastname);

    List<Author> findByFirstname(String firstname);

    List<Author> findByMiddlename(String middlename);

    List<Author> findByDateOfBirth(LocalDate dateOfBirth);
}
