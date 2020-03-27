package oose.dea.service;

import oose.dea.dao.ITokenDAO;

import javax.inject.Inject;

public class TokenService {

    @Inject
    private ITokenDAO iTokenDAO;

    public boolean tokenVerified(String token) {
        return iTokenDAO.verifyToken(token);
    }

    public String getUsernameByToken(String token) {
        return iTokenDAO.getUsername(token);
    }
}
