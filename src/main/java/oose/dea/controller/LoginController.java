package oose.dea.controller;

import oose.dea.controller.dto.TokenDTO;
import oose.dea.controller.dto.LoginRequestDTO;
import oose.dea.exceptions.TokenValidationException;
import oose.dea.service.LoginService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("login")
public class LoginController {

    private LoginService loginService;
    private Logger LOGGER = Logger.getLogger(getClass().getName());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO) {
        try {
            TokenDTO token = loginService.login(loginRequestDTO.getUser(), loginRequestDTO.getPassword());
            if (token == null) throw new TokenValidationException("No user token given");

            return Response.status(200).entity(token).build();
        } catch (TokenValidationException e) {
            LOGGER.severe(e.toString());
            return Response.status(400).build();
        }
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
