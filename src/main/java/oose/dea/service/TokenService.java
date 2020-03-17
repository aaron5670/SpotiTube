package oose.dea.service;

import oose.dea.dao.ITokenDAO;

import javax.inject.Inject;

public class TokenService {

    private ITokenDAO iTokenDAO;

    public boolean verifyToken(String token) {
        return iTokenDAO.verifyToken(token);
    }

    public String getUsernameByToken(String token) {
        return iTokenDAO.getUsername(token);
    }

    @Inject
    public void setiTokenDAO(ITokenDAO iTokenDAO) {
        this.iTokenDAO = iTokenDAO;
    }
}
