package oose.dea.controller;

import oose.dea.dto.TokenDTO;
import oose.dea.dto.LoginRequestDTO;
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
        TokenDTO token = loginService.login(loginRequestDTO.getUser(), loginRequestDTO.getPassword());
        if (token == null) {
            return Response.status(401).build();
        }

        return Response.status(200).entity(token).build();
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
