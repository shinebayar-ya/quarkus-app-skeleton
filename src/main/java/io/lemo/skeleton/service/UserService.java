package io.lemo.skeleton.service;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import io.lemo.skeleton.entity.User;
import io.lemo.skeleton.model.request.CreateUserRequest;
import io.lemo.skeleton.model.request.UpdateUserRequest;
import io.lemo.skeleton.model.request.UserQueryRequest;
import io.lemo.skeleton.repository.UserRepository;

public class UserService {

  @Inject
  UserRepository userRepository;

  public List<User> listUsers(UserQueryRequest filter) {
    return userRepository.listUsers(filter);
  }

  public long countUsers(UserQueryRequest request) {
    return userRepository.countUsers(request);
  }

  public User getUserById(Long id) {
    return userRepository.findById(id);
  }

  public User createUser(CreateUserRequest request) {
    User user = User.builder()
        .email(request.getEmail())
        .username(request.getUsername())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .build();

    userRepository.persist(user);
    return user;
  }

  public User updateUser(Long id, UpdateUserRequest request) {
    User user = userRepository.findByIdOptional(id).orElseThrow(
        () -> new NotFoundException("user.not_found"));

    user.setEmail(request.getEmail());
    user.setUsername(request.getUsername());
    userRepository.persist(user);
    return user;
  }
}
