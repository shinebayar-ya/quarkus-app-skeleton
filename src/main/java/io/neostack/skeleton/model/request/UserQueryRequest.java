package io.neostack.skeleton.model.request;

import java.util.Set;

import io.neostack.skeleton.common.BaseQueryRequest;
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
