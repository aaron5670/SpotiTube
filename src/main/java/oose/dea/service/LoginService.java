package oose.dea.service;

import oose.dea.dao.IUserDAO;
import oose.dea.controller.dto.TokenDTO;
import oose.dea.util.TokenGenerator;
import javax.inject.Inject;

public class LoginService {

    private TokenGenerator tokenGenerator = new TokenGenerator();
    private IUserDAO iUserDAO;

    public TokenDTO login(String username, String password) {

        if (iUserDAO.isAuthenticated(username, password)) {
            String token = tokenGenerator.generateToken();
            TokenDTO tokenDTO = new TokenDTO(username, token);

            iUserDAO.updateUserTokenInDatabase(username, token);

            return tokenDTO;
        } else {
            //ToDo: Throw custom exception...
            return null;
        }
    }

    @Inject
    public void setLoginService(IUserDAO iUserDAO) {
        this.iUserDAO = iUserDAO;
    }
}
