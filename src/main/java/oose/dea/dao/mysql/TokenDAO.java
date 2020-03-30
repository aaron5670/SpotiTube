package oose.dea.dao.mysql;

import oose.dea.dao.ITokenDAO;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

@Default
public class TokenDAO implements ITokenDAO {

    @Resource(name = "jdbc/spotitubeMySQL")
    private DataSource dataSource;
    private Logger LOGGER = Logger.getLogger(getClass().getName());

    @Override
    public boolean verifyToken(String token) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT token FROM tokens WHERE token = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.first();

        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            throw new InternalServerErrorException();
        }
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
            else
                return null;

        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            throw new InternalServerErrorException();
        }
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
            else
                return null;

        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            throw new InternalServerErrorException();
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}