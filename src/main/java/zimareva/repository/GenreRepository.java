package zimareva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zimareva.model.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
    List<Genre> findByName(String name);
}
