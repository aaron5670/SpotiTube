package oose.dea.dao;

import oose.dea.dao.mysql.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserDAOTest {
    public static final String USERNAME = "aaron";
    public static final String PASSWORD = "password";
    public static final String TOKEN = "123-456-789";

    @InjectMocks
    private static UserDAO sut;

    @Mock
    private static DataSource dataSource;

    @Mock
    private static Connection connection;

    @Mock
    private static PreparedStatement preparedStatement;

    @Mock
    private static ResultSet resultSet;

    private String expectedSQL;

    @BeforeEach
    public void setup() {
        initMocks(this);
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
