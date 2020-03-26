package oose.dea.controller;

import oose.dea.controller.dto.TokenDTO;
import oose.dea.controller.dto.LoginRequestDTO;
import oose.dea.exceptions.UnauthorizedException;
import oose.dea.service.LoginService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class LoginController {

    private LoginService loginService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO) {
        TokenDTO token = loginService.login(loginRequestDTO.user, loginRequestDTO.password);
        if (token == null) throw new UnauthorizedException("Invalid credentials");

        return Response.status(200).entity(token).build();
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
