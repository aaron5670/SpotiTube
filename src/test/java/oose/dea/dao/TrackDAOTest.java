package oose.dea.dao;

import oose.dea.domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TrackDAOTest {
    public static final String TOKEN = "123-456-789";
    public static final int PLAYLIST_ID = 1;
    public static final int TRACK_ID = 1;
    public static final String TRACK_TITLE = "Blinding Lights";
    public static final String TRACK_PERFORMER = "The Weeknd";
    public static final int TRACK_DURATION = 200;
    public static final String TRACK_DESCRIPTION = "Song about Blinding Lights";

    @InjectMocks
    private static TrackDAO sut;

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
    public void getAllTrackTest() {
        try {
            expectedSQL = "SELECT * FROM tracks " +
                    "LEFT JOIN playlist_tracks pt ON tracks.id = pt.trackId " +
                    "WHERE tracks.id NOT IN (SELECT trackid FROM playlist_tracks WHERE playlistid = ?)";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("id")).thenReturn(TRACK_ID);
            when(resultSet.getString("title")).thenReturn(TRACK_TITLE);
            when(resultSet.getString("performer")).thenReturn(TRACK_PERFORMER);
            when(resultSet.getInt("duration")).thenReturn(TRACK_DURATION);
            when(resultSet.getString("description")).thenReturn(TRACK_DESCRIPTION);

            // Act
            List<Track> actual = sut.getAllTracks(PLAYLIST_ID, true, TOKEN);

            List<Track> tracks = new ArrayList<>();
            Track track = new Track();
            track.setId(TRACK_ID);
            track.setTitle(TRACK_TITLE);
            track.setPerformer(TRACK_PERFORMER);
            track.setDuration(TRACK_DURATION);
            tracks.add(track);

            // Assert
            assertEquals(tracks.size(), actual.size());
            assertEquals(tracks.get(0).getId(), actual.get(0).getId());
            assertEquals(tracks.get(0).getTitle(), actual.get(0).getTitle());
            assertEquals(tracks.get(0).getPerformer(), actual.get(0).getPerformer());
            assertEquals(tracks.get(0).getDuration(), actual.get(0).getDuration());

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, PLAYLIST_ID);
            verify(preparedStatement).executeQuery();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getAllTracksTrowsError() {
        try {
            // Assign
            when(dataSource.getConnection()).thenThrow(new SQLException());

            // Act
            sut.getAllTracks(PLAYLIST_ID, true, TOKEN);

            // Assert
            assertThrows(SQLException.class, () -> {
                sut.getAllTracks(PLAYLIST_ID, true, TOKEN);
            });

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void removeTrackFromPlaylistTest() {
        try {
            expectedSQL = "DELETE FROM playlist_tracks WHERE playlistId = ? AND trackId = ?";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            // Act
            sut.removeTrackFromPlaylist(PLAYLIST_ID, TRACK_ID);

            // Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, PLAYLIST_ID);
            verify(preparedStatement).setInt(2, TRACK_ID);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void removeTrackFromPlaylistTrowsError() {
        try {
            // Assign
            when(dataSource.getConnection()).thenThrow(new SQLException());

            // Act
            sut.removeTrackFromPlaylist(PLAYLIST_ID, TRACK_ID);

            // Assert
            assertThrows(SQLException.class, () -> {
                sut.removeTrackFromPlaylist(PLAYLIST_ID, TRACK_ID);
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void addTrackToPlaylistTest() {
        try {
            expectedSQL = "INSERT INTO playlist_tracks (playlistId, trackId, offlineAvailable) VALUES (?, ?, ?)";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            // Act
            sut.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, true);

            // Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, PLAYLIST_ID);
            verify(preparedStatement).setInt(2, TRACK_ID);
            verify(preparedStatement).setBoolean(3, true);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void addTrackToPlaylistTrowsError() {
        try {
            // Assign
            when(dataSource.getConnection()).thenThrow(new SQLException());

            // Act
            sut.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, true);

            // Assert
            assertThrows(SQLException.class, () -> {
                sut.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID, true);
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
