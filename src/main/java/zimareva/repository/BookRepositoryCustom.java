package zimareva.repository;

import zimareva.model.Book;

import java.util.List;
import java.util.Map;

public interface BookRepositoryCustom {
    List<Book> findBooksByParameters(Map<String, Object> bookParameters);
}
