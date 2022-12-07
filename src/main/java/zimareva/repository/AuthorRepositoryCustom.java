package zimareva.repository;

import zimareva.model.Author;

import java.util.List;
import java.util.Map;

public interface AuthorRepositoryCustom {
    List<Author> findAuthorsByParameteres(Map<String, Object> authorParameteres);
}
