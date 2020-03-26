package oose.dea.controller;

import oose.dea.controller.dto.PlaylistDTO;
import oose.dea.controller.dto.PlaylistsDTO;
import oose.dea.dao.IPlaylistDAO;
import oose.dea.domain.Playlist;
import oose.dea.exceptions.ForbiddenException;
import oose.dea.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistControllerTest {

    public static final String TOKEN = "123-456-789";
    public static final int PLAYLIST_ID = 1;

    public static PlaylistController sut;
    public static TokenService service;
    private static IPlaylistDAO iPlaylistDAO;
    private static PlaylistsDTO playlistsDTO;
    private static PlaylistDTO playlistDTO;

    @BeforeEach
    public void setup() {
        sut = new PlaylistController();
        service = mock(TokenService.class);
        iPlaylistDAO = mock(IPlaylistDAO.class);

        sut.setTokenService(service);
        sut.setiPlaylistDAO(iPlaylistDAO);

        playlistDTO = new PlaylistDTO();
        playlistDTO.id = 1;
        playlistDTO.name = "HipHop playlist";
        playlistDTO.owner = true;

        playlistsDTO = new PlaylistsDTO();
        playlistsDTO.playlists = new ArrayList<>();
        playlistsDTO.playlists.add(playlistDTO);
        playlistsDTO.length = 500;
    }

    @Test
    public void getAllPlaylistsThrowUnauthorizedException() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenThrow(new ForbiddenException("Invalid user token"));

        // Asserts
        assertThrows(ForbiddenException.class, () -> {
            sut.getAllPlaylists(TOKEN);
        });
    }

    @Test
    public void getAllPlaylistsReturnsWith200() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenReturn(true);

        // Act
        Response actual = sut.getAllPlaylists(TOKEN);

        // Asserts
        assertEquals(200, actual.getStatus());
    }

    @Test
    public void deleteAPlaylistThrowUnauthorizedException() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenThrow(new ForbiddenException("Invalid user token"));

        // Asserts
        assertThrows(ForbiddenException.class, () -> {
            sut.deleteAPlaylist(PLAYLIST_ID, TOKEN);
        });
    }

    @Test
    public void deleteAPlaylistReturnsWith200() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenReturn(true);

        // Act
        Response actual = sut.deleteAPlaylist(PLAYLIST_ID, TOKEN);

        // Asserts
        assertEquals(200, actual.getStatus());
    }

    @Test
    public void addAPlaylistThrowUnauthorizedException() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenThrow(new ForbiddenException("Invalid user token"));

        // Asserts
        assertThrows(ForbiddenException.class, () -> {
            sut.addAPlaylist(playlistDTO, TOKEN);
        });
    }

    @Test
    public void addAPlaylistReturnsWith201() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenReturn(true);

        // Act
        Response actual = sut.addAPlaylist(playlistDTO, TOKEN);

        // Asserts
        assertEquals(201, actual.getStatus());
    }

    @Test
    public void editAPlaylistThrowUnauthorizedException() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenThrow(new ForbiddenException("Invalid user token"));

        // Asserts
        assertThrows(ForbiddenException.class, () -> {
            sut.editAPlaylist(playlistDTO, TOKEN);
        });
    }

    @Test
    public void editAPlaylistReturnsWith200() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenReturn(true);

        // Act
        Response actual = sut.editAPlaylist(playlistDTO, TOKEN);

        // Asserts
        assertEquals(200, actual.getStatus());
    }

    @Test
    public void playlistDTOReturnsSinglePlaylist(){
        // Arrange
        List<Playlist> playlistList = new ArrayList<>();

        Playlist playlist = new Playlist();
        playlist.setId(playlistDTO.id);
        playlist.setOwner(playlistDTO.owner);
        playlist.setTotalDuration(playlistsDTO.length);
        playlist.setName(playlistDTO.name);

        playlistList.add(playlist);

        when(iPlaylistDAO.getAllPlaylists(TOKEN)).thenReturn(playlistList);

        // Act
        PlaylistsDTO playlistsDTO = sut.playlistsDTO(TOKEN);
        PlaylistDTO actual = playlistsDTO.playlists.get(0);

        // Asserts
        assertEquals(playlist.getId(), actual.id);
        assertEquals(playlist.getName(), actual.name);
        assertEquals(playlist.getTotalDuration(), playlistsDTO.length);
        assertEquals(playlist.isOwner(), actual.owner);
    }
}
