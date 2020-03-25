package oose.dea.controller;

import oose.dea.service.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.when;

public class PlaylistControllerTest {

    public static final String TOKEN = "123-456-789";

    public static PlaylistController sut;
    public static TokenService service;

    @BeforeAll
    public static void setup() {
        sut = new PlaylistController();
        service = Mockito.mock(TokenService.class);
        sut.setTokenService(service);
    }

    @Test
    public void getAllPlaylistsReturnsWith403() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenReturn(false);

        // Act
        Response actual = sut.getAllPlaylists(TOKEN);

        // Asserts
        Assertions.assertEquals(403, actual.getStatus());
    }

}
