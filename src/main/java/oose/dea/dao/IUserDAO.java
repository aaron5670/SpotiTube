package oose.dea.dao;

public interface IUserDAO {

    /**
     *
     * @param username
     * @param password
     * @return if user is authenticated
     */
    boolean isAuthenticated(String username, String password);

    /**
     *
     * @param username
     * @param Token
     */
    void updateUserTokenInDatabase(String username, String Token);

    /**
     *
     * @param username
     * @return current saved user token
     */
    String getToken(String username);
}
