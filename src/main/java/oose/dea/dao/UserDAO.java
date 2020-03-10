package oose.dea.dao;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.*;

@Default
public class UserDAO implements IUserDAO {

    @Resource(name = "jdbc/spotitubeMySQL")
    DataSource dataSource;

    @Override
    public boolean isAuthenticated(String username, String password) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.first()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public void updateUserTokenInDatabase(String username, String token) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE users SET token = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getToken(String username) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT token FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.first()) {
                return resultSet.getString("token");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}