package oose.dea.dao;

import oose.dea.domain.Playlist;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Default
public class PlaylistDAO implements IPlaylistDAO {

    @Resource(name = "jdbc/spotitubeMySQL")
    private DataSource dataSource;
    private Logger LOGGER = Logger.getLogger(getClass().getName());

    @Override
    public List<Playlist> getAllPlaylists(String token) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT p.id, p.name, t.token, (SELECT SUM(duration) FROM tracks INNER JOIN playlist_tracks pt on tracks.id = pt.trackId WHERE pt.playlistId = p.id) AS length\n" +
                    "FROM playlists p\n" +
                    "INNER JOIN users u on p.owner = u.username\n" +
                    "LEFT OUTER JOIN tokens t on u.username = t.username";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Playlist> playlists = new ArrayList<>();

            while (resultSet.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(resultSet.getInt("p.id"));
                playlist.setName(resultSet.getString("p.name"));
                if (resultSet.getString("t.token").equals(token))
                    playlist.setOwner(true);
                 else
                    playlist.setOwner(false);

                playlist.setTotalDuration(resultSet.getInt("length"));
                playlists.add(playlist);
            }
            return playlists;
        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            throw new InternalServerErrorException();
        }
    }

    @Override
    public void deleteAPlaylist(int id, String token) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM playlists WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            throw new InternalServerErrorException();
        }
    }

    @Override
    public void addAPlaylist(String playlistName, String username, String token) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO playlists (name, owner) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlistName);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            throw new InternalServerErrorException();
        }
    }

    @Override
    public void editAPlaylist(String playlistName, int playlistId, String token) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE playlists SET name = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlistName);
            preparedStatement.setInt(2, playlistId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            throw new InternalServerErrorException();
        }
    }
}
