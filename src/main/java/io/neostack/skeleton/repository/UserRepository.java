package io.neostack.skeleton.repository;

import java.util.ArrayList;
import java.util.List;

import io.neostack.skeleton.entity.User;
import io.neostack.skeleton.model.request.UserQueryRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;

public class UserRepository extends BaseRepository<User> {

  public PanacheQuery<User> buildQuery(UserQueryRequest filter) {
    Parameters params = new Parameters();
    List<String> query = new ArrayList<>();

    if (filter.getEmail() != null) {
      query.add("email = :email");
      params.and("email", filter.getEmail());
    }

    String jpqlQuery = String.join(" and ", query);
    return find(jpqlQuery, sort(filter), params);
  }

  public List<User> listUsers(UserQueryRequest filter) {
    PanacheQuery<User> query = buildQuery(filter);
    query = page(query, filter);

    return query.list();
  }

  public long countUsers(UserQueryRequest request) {
    PanacheQuery<User> query = buildQuery(request);

    return query.count();
  }
}
