package oose.dea.controller;

import oose.dea.controller.dto.LoginRequestDTO;
import oose.dea.controller.dto.TokenDTO;
import oose.dea.service.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    public static final String USERNAME = "user";
    public static final String PASSWORD = "password";
    public static final String TOKEN = "123-456-789";

    public static LoginController sut;
    public static LoginService service;

    @BeforeAll
    public static void setup() {
        sut = new LoginController();
        service = Mockito.mock(LoginService.class);
        sut.setLoginService(service);
    }

    @Test
    void loginWithInvalidCredentialsRespondsWith401() {
        // Arrange
        when(service.login(USERNAME, PASSWORD)).thenReturn(null);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.user = USERNAME;
        loginRequestDTO.password = PASSWORD;

        // Act
        Response actual = sut.login(loginRequestDTO);

        // Assert
        Assertions.assertEquals(401, actual.getStatus());
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
        service = Mockito.mock(LoginService.class);

        sut.setLoginService(service);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.user = USERNAME;
        loginRequestDTO.password = PASSWORD;

        // Act
        sut.login(loginRequestDTO);

        // Assert
        verify(service).login(USERNAME, PASSWORD);
    }
}
