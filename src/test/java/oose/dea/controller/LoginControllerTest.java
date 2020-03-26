package oose.dea.controller;

import oose.dea.controller.dto.LoginRequestDTO;
import oose.dea.controller.dto.TokenDTO;
import oose.dea.exceptions.UnauthorizedException;
import oose.dea.service.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    public static final String USERNAME = "aaron";
    public static final String PASSWORD = "password";
    public static final String TOKEN = "123-456-789";

    public static LoginController sut;
    public static LoginService service;

    @BeforeEach
    public void setup() {
        sut = new LoginController();
        service = Mockito.mock(LoginService.class);
        sut.setLoginService(service);
    }

    @Test
    void loginWithInvalidCredentialsThrowUnauthorizedException() {
        // Arrange
        when(service.login(USERNAME, PASSWORD)).thenThrow(new UnauthorizedException("Invalid credentials"));

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.user = USERNAME;
        loginRequestDTO.password = PASSWORD;

        // Assert
        assertThrows(UnauthorizedException.class, () -> {
            sut.login(loginRequestDTO);
        });
    }

    @Test
    void loginWithValidCredentialsRespondsWith200() {
        // Arrange
        TokenDTO tokenDTO = new TokenDTO(USERNAME, TOKEN);

        when(service.login(USERNAME, PASSWORD)).thenReturn(tokenDTO);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.user = USERNAME;
        loginRequestDTO.password = PASSWORD;

        // Act
        Response actual = sut.login(loginRequestDTO);

        // Assert
        Assertions.assertEquals(200, actual.getStatus());
    }

    @Test
    void loginDelegatesToService() {
        // Arrange
        TokenDTO tokenDTO = new TokenDTO(USERNAME, TOKEN);
        when(service.login(USERNAME, PASSWORD)).thenReturn(tokenDTO);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.user = USERNAME;
        loginRequestDTO.password = PASSWORD;

        // Act
        sut.setLoginService(service);
        sut.login(loginRequestDTO);

        // Assert
        verify(service).login(USERNAME, PASSWORD);
    }
}
