package oose.dea.service;

import oose.dea.controller.dto.TokenDTO;
import oose.dea.dao.IUserDAO;
import oose.dea.util.TokenGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    public static final String USERNAME = "user";
    public static final String PASSWORD = "password";

    public static LoginService sut;
    public static IUserDAO iUserDAO;
    public static TokenGenerator tokenGenerator;

    @BeforeAll
    public static void setup() {
        sut = new LoginService();
        iUserDAO = Mockito.mock(IUserDAO.class);
        sut.setiUserDAO(iUserDAO);
    }

    @Test
    public void userIsNotAuthenticatedReturnsNull() {
        // Arrange
        when(!iUserDAO.isAuthenticated(USERNAME, PASSWORD)).thenReturn(false);

        // Act
        TokenDTO actual = sut.login(USERNAME, PASSWORD);

        // Assert
        Assertions.assertNull(actual);
    }

    @Test
    public void updateUserTokenInDatabase() {
        // Arrange
        when(iUserDAO.isAuthenticated(USERNAME, PASSWORD)).thenReturn(true);
        tokenGenerator = new TokenGenerator();
        String token = tokenGenerator.generateToken();

        // Act
        iUserDAO.updateUserTokenInDatabase(USERNAME, token);

        // Assert
        verify(iUserDAO).updateUserTokenInDatabase(USERNAME, token);
    }
}
