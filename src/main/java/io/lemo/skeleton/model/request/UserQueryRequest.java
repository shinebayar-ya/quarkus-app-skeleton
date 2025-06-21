package io.lemo.skeleton.model.request;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQueryRequest extends BaseQueryRequest {
  private String email;

  @Override
  public Set<String> getSortableFields() {
    return Set.of("id", "username", "firstName", "lastName", "createdAt", "updatedAt");
  }
}
