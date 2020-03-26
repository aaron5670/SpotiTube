package oose.dea.dao;

import oose.dea.domain.Playlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlaylistDAOTest {
    public static final String TOKEN = "123-456-789";
    public static final int PLAYLIST_ID = 1;
    public static final String PLAYLIST_NAME = "HipHop Playlist";
    public static final String USERNAME = "aaron";

    @InjectMocks
    private static PlaylistDAO sut;

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
    public void getAllPlaylistsReturnsAllPlaylists() {
        try {
            expectedSQL = "SELECT p.id, p.name, t.token, (SELECT SUM(duration) FROM tracks INNER JOIN playlist_tracks pt on tracks.id = pt.trackId WHERE pt.playlistId = p.id) AS length\n" +
                    "FROM playlists p\n" +
                    "INNER JOIN users u on p.owner = u.username\n" +
                    "LEFT OUTER JOIN tokens t on u.username = t.username";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            int id = 13;
            String songname = "The weekend";

            List<Playlist> playlistList = sut.getAllPlaylists(TOKEN);

            Playlist playlistMocked = mock(Playlist.class);
            playlistMocked.setName("The Weekend");
            playlistMocked.setOwner(true);
            playlistMocked.setId(13);
            playlistMocked.setTotalDuration(200);
            playlistList.add(playlistMocked);

            when(resultSet.getInt("p.id")).thenReturn(id);
            when(resultSet.getString("p.name")).thenReturn(songname);
            when(resultSet.getString("t.token").equals(TOKEN)).thenReturn(true);
            when(resultSet.getInt("length")).thenReturn(21);

            assertEquals(resultSet.getInt("p.id"), id);
            assertEquals(resultSet.getString("p.name"), songname);
            assertEquals(resultSet.getInt("length"), 21);
            assertEquals(resultSet.getBoolean("owner"), playlistMocked.isOwner());

            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).executeQuery();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void getAllPlaylistsTrowsError() {
        try {
            // Assign
            when(dataSource.getConnection()).thenThrow(new SQLException());

            // Act
            sut.getAllPlaylists(TOKEN);

            // Assert
            assertThrows(SQLException.class, () -> {
                sut.getAllPlaylists(TOKEN);
            });

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void deleteAPlaylistTest() {
        try {
            expectedSQL = "DELETE FROM playlists WHERE id = ?";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            // Act
            sut.deleteAPlaylist(PLAYLIST_ID, TOKEN);

            // Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, PLAYLIST_ID);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void deleteAPlaylistTrowsError() {
        try {
            // Assign
            when(dataSource.getConnection()).thenThrow(new SQLException());

            // Act
            sut.deleteAPlaylist(PLAYLIST_ID, TOKEN);

            // Assert
            assertThrows(SQLException.class, () -> {
                sut.deleteAPlaylist(PLAYLIST_ID, TOKEN);
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void addAPlaylistTest() {
        try {
            expectedSQL = "INSERT INTO playlists (name, owner) VALUES (?, ?)";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            // Act
            sut.addAPlaylist(PLAYLIST_NAME, USERNAME, TOKEN);

            // Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, PLAYLIST_NAME);
            verify(preparedStatement).setString(2, USERNAME);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void addAPlaylistTrowsError() {
        try {
            // Assign
            when(dataSource.getConnection()).thenThrow(new SQLException());

            // Act
            sut.addAPlaylist(PLAYLIST_NAME, USERNAME, TOKEN);

            // Assert
            assertThrows(SQLException.class, () -> {
                sut.addAPlaylist(PLAYLIST_NAME, USERNAME, TOKEN);
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void editAPlaylistTest() {
        try {
            expectedSQL = "UPDATE playlists SET name = ? WHERE id = ?";

            // Assign
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            // Act
            sut.editAPlaylist(PLAYLIST_NAME, PLAYLIST_ID, TOKEN);

            // Assert
            verify(dataSource).getConnection();
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, PLAYLIST_NAME);
            verify(preparedStatement).setInt(2, PLAYLIST_ID);
            verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void editAPlaylistTrowsError() {
        try {
            // Assign
            when(dataSource.getConnection()).thenThrow(new SQLException());

            // Act
            sut.editAPlaylist(PLAYLIST_NAME, PLAYLIST_ID, TOKEN);

            // Assert
            assertThrows(SQLException.class, () -> {
                sut.editAPlaylist(PLAYLIST_NAME, PLAYLIST_ID, TOKEN);
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
