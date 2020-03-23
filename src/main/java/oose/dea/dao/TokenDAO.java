package oose.dea.dao;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Default
public class TokenDAO implements ITokenDAO {

    @Resource(name = "jdbc/spotitubeMySQL")
    DataSource dataSource;

    @Override
    public boolean verifyToken(String token) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT token FROM tokens WHERE token = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, token);
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
    public String getToken(String username) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT token FROM users INNER JOIN tokens t on users.username = t.username WHERE users.username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.first())
                return resultSet.getString("token");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public String getUsername(String token) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT username FROM tokens where token = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.first())
                return resultSet.getString("username");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}