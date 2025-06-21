package io.neostack.skeleton.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
  private String username;
  private String email;
}
