package oose.dea.dao;

public interface ITokenDAO {

    /**
     *
     * @param token valid user token
     * @return true if token is in database
     */
    public boolean verifyToken(String token);

    /**
     *
     * @param username valid username
     * @return current saved user token
     */
    String getToken(String username);
}
