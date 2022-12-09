package zimareva.repository;

import zimareva.model.Book;
import zimareva.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> findBooksByParameteres(Map<String, Object> bookParameteres) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();
        bookParameteres.forEach((key, value) -> {
            if (key.equals("genre")) {
                Join<Book, Genre> genreJoin = book.join("genre");
                predicates.add(cb.equal(genreJoin.get("name"), value));
            } else {
                predicates.add(cb.equal(book.get(key), value));
            }
        });
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList();
    }
}
