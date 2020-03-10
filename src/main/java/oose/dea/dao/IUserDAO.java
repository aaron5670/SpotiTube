package oose.dea.dao;

public interface IUserDAO {
    //ToDo: Generate Java doc
    boolean authenticate(String username, String password);

    //ToDo: Generate Java doc
    void updateUserTokenInDatabase(String username, String Token);
}
