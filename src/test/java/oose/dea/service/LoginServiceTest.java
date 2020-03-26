package oose.dea.service;

import oose.dea.controller.dto.TokenDTO;
import oose.dea.dao.IUserDAO;
import oose.dea.util.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoginServiceTest {

    public static final String USERNAME = "aaron";
    public static final String PASSWORD = "password";

    @InjectMocks
    public static LoginService sut;

    @Mock
    public static IUserDAO iUserDAO;

    @Mock
    public static TokenGenerator tokenGenerator;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void userIsNotAuthenticatedReturnsNull() {
        // Arrange
        when(!iUserDAO.isAuthenticated(USERNAME, PASSWORD)).thenReturn(false);

        // Act
        TokenDTO actual = sut.login(USERNAME, PASSWORD);

        // Assert
        assertNull(actual);
    }

    @Test
    public void updateUserTokenInDatabase() {
        // Arrange
        when(iUserDAO.isAuthenticated(USERNAME, PASSWORD)).thenReturn(true);
        String token = tokenGenerator.generateToken();

        // Act
        sut.login(USERNAME, PASSWORD);

        // Assert
        verify(iUserDAO).updateUserTokenInDatabase(USERNAME, token);
    }
}
