package io.neostack.skeleton.resource;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import io.neostack.skeleton.service.UserService;

@RequestScoped
@Path("/users")
public class UserResource {

  @Inject
  UserService userService;

  @GET
  public Response getUsers() {
    return Response.ok().build();
  }

  @POST
  public Response createUser() {
    return Response.status(Response.Status.CREATED).build();
  }

  @GET
  @Path("/{id}")
  public Response getUserById(Long id) {
    return Response.ok().build();
  }

  @PUT
  @Path("/{id}")
  public Response updateUser(Long id) {
    return Response.ok().build();
  }
}
