package io.neostack.skeleton.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
  private Long id;
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String updatedAt;
  private String createdAt;
}
