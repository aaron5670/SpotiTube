package oose.dea.dao;

import oose.dea.dao.mysql.TokenDAO;
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

public class TokenDAOTest {
    public static final String USERNAME = "aaron";
    public static final String TOKEN = "123-456-789";

    @InjectMocks
    private static TokenDAO sut;

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
    public void verifyTokenReturnsTrue() {
        try {
            expectedSQL = "SELECT token FROM tokens WHERE token = ?";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.first()).thenReturn(true);

            // Act
            boolean actual = sut.verifyToken(TOKEN);

            // Assert
            assertTrue(actual);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, TOKEN);
            verify(preparedStatement).executeQuery();
        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void verifyTokenThrowsError() {
        try {
            // Assign
            when(dataSource.getConnection()).thenThrow(new SQLException());

            // Act
            sut.verifyToken(TOKEN);

            // Assert
            assertThrows(SQLException.class, () -> {
                        sut.verifyToken(TOKEN);
                    }
            );
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void getTokenReturnsToken() {
        try {
            expectedSQL = "SELECT token FROM users INNER JOIN tokens t on users.username = t.username WHERE users.username = ?";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.first()).thenReturn(true);
            when(resultSet.getString("token")).thenReturn(TOKEN);

            // Act
            String actual = sut.getToken(USERNAME);

            // Assert
            assertEquals(TOKEN, actual);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, USERNAME);
            verify(preparedStatement).executeQuery();
        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void getTokenReturnsNull() {
        try {
            expectedSQL = "SELECT token FROM users INNER JOIN tokens t on users.username = t.username WHERE users.username = ?";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.first()).thenReturn(false);

            // Act
            String actual = sut.getToken(USERNAME);

            // Assert
            assertNull(actual);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, USERNAME);
            verify(preparedStatement).executeQuery();
        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void getTokenThrowsError() {
        try {
            // Assign
            when(dataSource.getConnection()).thenThrow(new SQLException());

            // Act
            sut.getToken(USERNAME);

            // Assert
            assertThrows(SQLException.class, () -> {
                        sut.getToken(USERNAME);
                    }
            );
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void getUsernameReturnsUsername() {
        try {
            expectedSQL = "SELECT username FROM tokens where token = ?";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.first()).thenReturn(true);
            when(resultSet.getString("username")).thenReturn(USERNAME);

            // Act
            String actual = sut.getUsername(TOKEN);

            // Assert
            assertEquals(USERNAME, actual);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, TOKEN);
            verify(preparedStatement).executeQuery();
        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void getUsernameReturnsNull() {
        try {
            expectedSQL = "SELECT username FROM tokens where token = ?";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.first()).thenReturn(false);

            // Act
            String actual = sut.getUsername(TOKEN);

            // Assert
            assertNull(actual);

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, TOKEN);
            verify(preparedStatement).executeQuery();
        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void getUsernameThrowsError() {
        try {
            // Assign
            when(dataSource.getConnection()).thenThrow(new SQLException());

            // Act
            sut.getUsername(TOKEN);

            // Assert
            assertThrows(SQLException.class, () -> {
                        sut.getToken(USERNAME);
                    }
            );
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
