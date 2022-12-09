package zimareva.repository;

import zimareva.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Author> findAuthorsByParameters(Map<String, Object> authorParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> query = cb.createQuery(Author.class);
        Root<Author> author  = query.from(Author.class);
        List<Predicate> predicates = new ArrayList<>();
        authorParameters.forEach((key, value) -> {
            if (key.equals("dateOfBirth")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateOfBirth = LocalDate.parse((String) value, formatter);
                predicates.add(cb.equal(author.get(key), dateOfBirth));
            } else {
                predicates.add(cb.equal(author.get(key), value));
            }
        });
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList();
    }
}
