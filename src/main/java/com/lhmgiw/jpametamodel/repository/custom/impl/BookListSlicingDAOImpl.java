package com.lhmgiw.jpametamodel.repository.custom.impl;

import com.lhmgiw.jpametamodel.entities.Book;
import com.lhmgiw.jpametamodel.entities.Book_;
import com.lhmgiw.jpametamodel.enums.StatusEnum;
import com.lhmgiw.jpametamodel.repository.custom.ListSlicingDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("bookListSlicingDAO")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookListSlicingDAOImpl implements ListSlicingDAO<Book> {

    private EntityManager entityManager;

    private static final String CODE = "code";
    private static final String NAME = "name";
    private static final String AUTHOR = "author";

    @Override
    public Optional<List<Book>> findAllParameters(Map<String, Object> paraMap) {
        log.info("bookListSlicingDAO - findAllParameters() called");
        List<Book> requests;

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = cb.createQuery(Book.class);

        /* ----- Set Table Name (FROM) -------- */
        Root<Book> root = criteriaQuery.from(Book.class);
        /* ----- Set Details (SELECT) -------- */
        criteriaQuery.select(root);

        /* ----- Set Conditions (WHERE) -------- */
        List<Predicate> predicates = createSearchPredicate(paraMap, cb, root);
        List<Predicate> allPredicates = createAllSearchPredicate(paraMap, cb, root);
        criteriaQuery.where(
                cb.and(predicates.toArray(new Predicate[predicates.size()])),
                cb.and(cb.or(allPredicates.toArray(new Predicate[allPredicates.size()]))),
                cb.notEqual(root.get(Book_.STATUS), StatusEnum.DELETE)
        );

        /* ----- Create sort order (ORDER_BY) -------- */
        criteriaQuery.orderBy(createSortOrder(Optional.ofNullable((String) paraMap.get(SORT_BY)).orElse(""),
                Optional.ofNullable((String) paraMap.get("order")).orElse("asc"), cb, root)
        );

        /* ----- Set Start Position -------- */
        TypedQuery<Book> typedQuery = entityManager.createQuery(criteriaQuery);
        int start = Optional.ofNullable((Integer) paraMap.get(START)).orElse(0)> 0? (Integer) paraMap.get(START): 0;
        typedQuery.setFirstResult(start);

        /* ----- Set End Position -------- */
        if (paraMap.get(LIMIT) != null && (Integer) paraMap.get(LIMIT)> 0)
            typedQuery.setMaxResults((Integer) paraMap.get(LIMIT));

        requests = typedQuery.getResultList();
        log.info("bookListSlicingDAO - findAllParameters() completed");
        return Optional.of(requests);
    }

    @Override
    public Long rowCount(Map<String, Object> paraMap) {
        log.info("bookListSlicingDAO - rowCount() called");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

        /* ----- Set Table Name (FROM) -------- */
        Root<Book> root = criteriaQuery.from(Book.class);
        /* ----- Set Details (SELECT) -------- */
        criteriaQuery.select(cb.count(root));

        /* ----- Set Conditions (WHERE) -------- */
        List<Predicate> allPredicates = createAllSearchPredicate(paraMap, cb, root);
        List<Predicate> predicates = createSearchPredicate(paraMap, cb, root);
        criteriaQuery.where(
                cb.and(predicates.toArray(new Predicate[predicates.size()])),
                cb.and(cb.or(allPredicates.toArray(new Predicate[allPredicates.size()]))),
                cb.notEqual(root.get(Book_.STATUS), StatusEnum.DELETE)
        );

        log.info("bookListSlicingDAO - rowCount() completed");
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private List<Predicate> createAllSearchPredicate(Map<String, ?> searchParam, CriteriaBuilder cb, Root<Book> root){
        log.info("bookListSlicingDAO - createAllSearchPredicate() called");
        List<Predicate> predicates = new ArrayList<>();

        if (searchParam.get(ALL) != null){
            predicates.add(cb.like(root.get(Book_.CODE), "%" + searchParam.get(ALL) + "%"));
            predicates.add(cb.like(root.get(Book_.NAME), "%" + searchParam.get(ALL) + "%"));
            predicates.add(cb.like(root.get(Book_.AUTHOR), "%" + searchParam.get(ALL) + "%"));
        }

        if (predicates.isEmpty())
            predicates.add(cb.conjunction());
        log.info("bookListSlicingDAO - createAllSearchPredicate() completed");
        return predicates;
    }

    private List<Predicate> createSearchPredicate(Map<String, ?> searchParam, CriteriaBuilder cb, Root<Book> root){
        log.info("bookListSlicingDAO - createSearchPredicate() called");
        List<Predicate> predicates = new ArrayList<>();

        if (searchParam.get(CODE) != null){
            predicates.add(cb.like(root.get(Book_.CODE), "%" + searchParam.get(CODE) + "%"));
        }
        if (searchParam.get(NAME) != null) {
            predicates.add(cb.like(root.get(Book_.NAME), "%" + searchParam.get(NAME) + "%"));
        }
        if (searchParam.get(AUTHOR) != null) {
            predicates.add(cb.like(root.get(Book_.AUTHOR), "%" + searchParam.get(AUTHOR) + "%"));
        }

        log.info("bookListSlicingDAO - createSearchPredicate() completed");
        return predicates;
    }

    private List<Order> createSortOrder(String columnName, String order, CriteriaBuilder cb, Root<Book> root){
        log.info("bookListSlicingDAO - createSortOrder() called");
        List<Order> orders = new ArrayList<>();

        Expression<?> ex = root.get(Book_.UPDATED_ON);

        if (CODE.equals(columnName))
            ex = root.get(Book_.CODE);
        if (NAME.equals(columnName))
            ex = root.get(Book_.NAME);
        if (AUTHOR.equals(columnName))
            ex = root.get(Book_.AUTHOR);

        orders.add(("asc".equalsIgnoreCase(order)) ? cb.asc(ex): cb.desc(ex));
        log.info("bookListSlicingDAO - createSortOrder() completed");
        return orders;
    }
}
