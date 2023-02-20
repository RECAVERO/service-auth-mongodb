package org.nttdata.application.rest;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.nttdata.btask.interfaces.AuthService;
import org.nttdata.domain.models.AuthDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/auths")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
  private final AuthService authService;

  public AuthResource(AuthService authService) {
    this.authService = authService;
  }

  @GET
  public Multi<AuthDto> findAll() {
    return authService.list();
  }

  @POST
  public Uni<AuthDto> add(AuthDto authDto) {
    return authService.addAuth(authDto);
  }

  @PUT
  public Uni<AuthDto> updateCustomer(AuthDto authDto) {
    return authService.updateAuth(authDto);
  }

  @POST
  @Path("/search")
  public Uni<AuthDto> findByNroAccount(AuthDto authDto) {
    return authService.findByNroAuth(authDto);
  }

  @DELETE
  public Uni<AuthDto> deleteCustomer(AuthDto authDto) {
    return authService.deleteAuth(authDto);
  }
}
