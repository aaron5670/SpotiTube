package oose.dea.dao;

public interface ITokenDAO {

    /**
     *
     * @param token valid user token
     * @return true if token is in database
     */
    boolean verifyToken(String token);


    /**
     *
     * @param token valid user token
     * @return returns the username associated with the associated token
     */
    String getUsername(String token);
}
