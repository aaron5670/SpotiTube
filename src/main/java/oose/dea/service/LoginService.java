package oose.dea.service;

import oose.dea.dao.IUserDAO;
import oose.dea.controller.dto.TokenDTO;
import oose.dea.exceptions.TokenValidationException;
import oose.dea.exceptions.UserAuthenticationException;
import oose.dea.util.TokenGenerator;

import javax.inject.Inject;
import java.util.logging.Logger;

public class LoginService {

    private TokenGenerator tokenGenerator = new TokenGenerator();
    private IUserDAO iUserDAO;
    private Logger LOGGER = Logger.getLogger(getClass().getName());

    public TokenDTO login(String username, String password) {
        try {
            if (!iUserDAO.isAuthenticated(username, password))
                throw new UserAuthenticationException("Invalid user credentials");

            String token = tokenGenerator.generateToken();
            TokenDTO tokenDTO = new TokenDTO(username, token);
            iUserDAO.updateUserTokenInDatabase(username, token);

            return tokenDTO;
        } catch (TokenValidationException e) {
            LOGGER.severe(e.toString());
            return null;
        }
    }

    @Inject
    public void setLoginService(IUserDAO iUserDAO) {
        this.iUserDAO = iUserDAO;
    }
}
