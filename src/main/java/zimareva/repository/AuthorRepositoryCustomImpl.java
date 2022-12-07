package zimareva.repository;

import zimareva.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    //todo:проблема с фильтром по дате рождения
    @Override
    public List<Author> findAuthorsByParameteres(Map<String, Object> authorParameteres) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> query = cb.createQuery(Author.class);
        Root<Author> author  = query.from(Author.class);
        List<Predicate> predicates = new ArrayList<>();
        authorParameteres.forEach((key, value) -> {
            predicates.add(cb.equal(author.get(key), value));
        });
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList();
    }
}
