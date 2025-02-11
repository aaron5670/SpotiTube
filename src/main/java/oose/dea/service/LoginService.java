package oose.dea.service;

import oose.dea.dao.IUserDAO;
import oose.dea.controller.dto.TokenDTO;
import oose.dea.util.TokenGenerator;

import javax.inject.Inject;

public class LoginService {

    @Inject
    private IUserDAO iUserDAO;

    private TokenGenerator tokenGenerator = new TokenGenerator();

    public TokenDTO login(String username, String password) {
        if (!iUserDAO.isAuthenticated(username, password))
            return null;

        String token = tokenGenerator.generateToken();
        TokenDTO tokenDTO = new TokenDTO(username, token);
        iUserDAO.updateUserTokenInDatabase(username, token);

        return tokenDTO;
    }
}
