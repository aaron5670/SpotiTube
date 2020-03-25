package oose.dea.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOTest {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String TOKEN = "123-456-789";
    private static UserDAO sut;

    private static DataSource dataSource;
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    private String expectedSQL;

    @BeforeEach
    public void setup() {
        sut = new UserDAO();

        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void isAuthenticatedReturnsTrue() {
        try {
            expectedSQL = "SELECT * FROM users WHERE username = ? AND password = ?";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.first()).thenReturn(true);

            // Act
            sut.setDataSource(dataSource);
            boolean actual = sut.isAuthenticated(USERNAME, PASSWORD);

            // Assert
            assertTrue(actual);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, USERNAME);
            verify(preparedStatement).setString(2, PASSWORD);
            verify(preparedStatement).executeQuery();
        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void isAuthenticatedThrowsError() {
        try {
            // Assign
            when(dataSource.getConnection()).thenThrow(new SQLException());

            // Act
            sut.setDataSource(dataSource);
            sut.isAuthenticated(USERNAME, PASSWORD);

            // Assert
            assertThrows(SQLException.class, () -> {
                        sut.isAuthenticated(USERNAME, PASSWORD);
                    }
            );
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void updateUserTokenInDatabaseTest() {
        try {
            expectedSQL = "UPDATE tokens SET token = ? WHERE username = ?";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            // Act
            sut.setDataSource(dataSource);
            sut.updateUserTokenInDatabase(USERNAME, TOKEN);

            // Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, TOKEN);
            verify(preparedStatement).setString(2, USERNAME);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void updateUserTokenInDatabaseThrowsError() {
        try {
            // Assign
            when(dataSource.getConnection()).thenThrow(new SQLException());

            // Act
            sut.setDataSource(dataSource);
            sut.updateUserTokenInDatabase(USERNAME, TOKEN);

            // Assert
            assertThrows(SQLException.class, () -> {
                        sut.updateUserTokenInDatabase(USERNAME, TOKEN);
                    }
            );
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
