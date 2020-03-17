package oose.dea.dao;

public interface IUserDAO {

    /**
     *
     * @param username valid username
     * @param password valid user password
     * @return if user is authenticated
     */
    boolean isAuthenticated(String username, String password);

    /**
     *
     * @param username valid username
     * @param token valid user token
     */
    void updateUserTokenInDatabase(String username, String token);
}
