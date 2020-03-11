package oose.dea.dao;

public interface ITokenDAO {

    /**
     *
     * @param token user token
     * @return true if token is in database
     */
    public boolean verifyToken(String token);

    /**
     *
     * @param username
     * @return current saved user token
     */
    String getToken(String username);
}
