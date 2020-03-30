package oose.dea.dao.mysql;

import oose.dea.dao.IUserDAO;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.*;
import java.util.logging.Logger;

@Default
public class UserDAO implements IUserDAO {

    @Resource(name = "jdbc/spotitubeMySQL")
    private DataSource dataSource;
    private Logger LOGGER = Logger.getLogger(getClass().getName());

    @Override
    public boolean isAuthenticated(String username, String password) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.first();

        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            throw new InternalServerErrorException();
        }
    }

    @Override
    public void updateUserTokenInDatabase(String username, String token) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE tokens SET token = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            throw new InternalServerErrorException();
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
