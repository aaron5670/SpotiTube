package oose.dea.controller;

import oose.dea.controller.dto.TrackDTO;
import oose.dea.controller.dto.TracksDTO;
import oose.dea.dao.ITrackDAO;
import oose.dea.domain.Track;
import oose.dea.exceptions.ForbiddenException;
import oose.dea.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TracksControllerTest {

    public static final String TOKEN = "123-456-789";
    public static final int PLAYLIST_ID = 1;
    public static final int TRACK_ID = 1;

    @InjectMocks
    public static TracksController sut;

    @Mock
    public static TokenService service;

    @Mock
    private static ITrackDAO iTrackDAO;

    @Mock
    private static TrackDTO trackDTO;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void getAllTracksThrowUnauthorizedException() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenThrow(new ForbiddenException("Invalid user token"));

        // Asserts
        assertThrows(ForbiddenException.class, () -> {
            sut.getAllTracks(PLAYLIST_ID, TOKEN);
        });
    }

    @Test
    public void getAllTracksReturnsWith200() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenReturn(true);

        // Act
        Response actual = sut.getAllTracks(PLAYLIST_ID, TOKEN);

        // Asserts
        assertEquals(200, actual.getStatus());
    }

    @Test
    public void deleteATrackThrowUnauthorizedException() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenThrow(new ForbiddenException("Invalid user token"));

        // Asserts
        assertThrows(ForbiddenException.class, () -> {
            sut.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);
        });
    }

    @Test
    public void deleteATrackReturnsWith200() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenReturn(true);

        // Act
        Response actual = sut.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);

        // Asserts
        assertEquals(200, actual.getStatus());
    }

    @Test
    public void addATrackThrowUnauthorizedException() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenThrow(new ForbiddenException("Invalid user token"));

        // Asserts
        assertThrows(ForbiddenException.class, () -> {
            sut.addTrackToPlaylist(PLAYLIST_ID, trackDTO, TOKEN);
        });
    }

    @Test
    public void addATrackReturnsWith201() {
        // Arrange
        when(service.tokenVerified(TOKEN)).thenReturn(true);

        // Act
        Response actual = sut.addTrackToPlaylist(PLAYLIST_ID, trackDTO, TOKEN);

        // Asserts
        assertEquals(201, actual.getStatus());
    }

    @Test
    public void tracksDTOReturnsSinglePlaylist(){
        // Arrange
        List<Track> tracks = new ArrayList<>();

        Track track = new Track();
        track.setId(trackDTO.id);
        track.setTitle(trackDTO.title);
        track.setAlbum(trackDTO.album);
        track.setDescription(trackDTO.description);

        tracks.add(track);

        when(iTrackDAO.getAllTracks(PLAYLIST_ID, false, TOKEN)).thenReturn(tracks);

        // Act
        TracksDTO tracksDTO = sut.tracksDTO(PLAYLIST_ID, false, TOKEN);
        TrackDTO actual = tracksDTO.tracks.get(0);

        // Asserts
        assertEquals(track.getId(), actual.id);
        assertEquals(track.getTitle(), actual.title);
        assertEquals(track.getAlbum(), actual.album);
        assertEquals(track.getDescription(), actual.description);
    }
}
