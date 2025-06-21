package io.neostack.skeleton.repository;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

import io.neostack.skeleton.common.BaseQueryRequest;
import io.neostack.skeleton.model.dto.SortField;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;

/**
 * @author Lemo
 * @param <T>
 */
public abstract class BaseRepository<T> implements PanacheRepository<T> {

  protected Sort sort(BaseQueryRequest request) {
    List<SortField> sortFields = parseSortFields(request);
    Sort sort = Sort.by();

    for (SortField f : sortFields) {
      sort = sort.and(f.field(), f.descending() ? Direction.Descending : Direction.Ascending);
    }

    return sort;
  }

  protected <U> CriteriaQuery<U> sort(CriteriaBuilder cb, CriteriaQuery<U> cq, Root<T> root, BaseQueryRequest request) {
    List<SortField> sortFields = parseSortFields(request);
    if (sortFields.isEmpty()) {
      return cq;
    }

    List<Order> orders = sortFields.stream()
        .map(f -> f.descending() ? cb.desc(root.get(f.field())) : cb.asc(root.get(f.field())))
        .toList();

    return cq.orderBy(orders);
  }

  protected List<SortField> parseSortFields(BaseQueryRequest request) {
    if (!isValidSortRequest(request))
      return List.of();

    return Arrays.stream(request.getSort().split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .map(field -> {
          boolean desc = field.startsWith("-");
          String sortField = desc ? field.substring(1) : field;
          return new SortField(sortField, desc);
        })
        .filter(f -> request.getSortableFields().contains(f.field()))
        .toList();
  }

  private boolean isValidSortRequest(BaseQueryRequest request) {
    return request != null
        && request.getSort() != null
        && !request.getSort().isEmpty()
        && request.getSortableFields() != null
        && !request.getSortableFields().isEmpty();
  }

  protected PanacheQuery<T> page(PanacheQuery<T> query, BaseQueryRequest request) {
    return query.page(Page.of(request.getPage(), request.getSize()));
  }

  protected <Q extends Query> Q page(Q query, BaseQueryRequest request) {
    query.setFirstResult(request.getOffset());
    query.setMaxResults(request.getSize());
    return query;
  }

  public void clear() {
    getEntityManager().clear();
  }
}
