package oose.dea.controller;

import oose.dea.controller.dto.LoginRequestDTO;
import oose.dea.service.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    public static final String USER = "user";
    public static final String PASSWORD = "password";

    public static LoginController sut;
    public static LoginService service;

    @BeforeAll
    public static void setup() {
        sut = new LoginController();
        service = Mockito.mock(LoginService.class);
    }

    @Test
    void loginWithInvalidCredentialsRespondsWith400() {
        // Arrange
        sut.setLoginService(service);
        when(service.login(USER, PASSWORD)).thenReturn(null);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.user = USER;
        loginRequestDTO.password = PASSWORD;

        // Act
        Response actual = sut.login(loginRequestDTO);

        // Assert
        Assertions.assertEquals(401, actual.getStatus());
    }
}
