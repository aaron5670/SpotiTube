package oose.dea.dao.mysql;

import oose.dea.dao.ITrackDAO;
import oose.dea.domain.Track;

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
public class TrackDAO implements ITrackDAO {

    @Resource(name = "jdbc/spotitubeMySQL")
    private DataSource dataSource;
    private Logger LOGGER = Logger.getLogger(getClass().getName());

    @Override
    public List<Track> getAllTracks(int forPlaylist, boolean addTracks, String token) {
        try (Connection connection = dataSource.getConnection()) {
            String sql;
            if (addTracks) {
                sql = "SELECT * FROM tracks LEFT JOIN playlist_tracks pt ON tracks.id = pt.trackId WHERE tracks.id NOT IN (SELECT trackid FROM playlist_tracks WHERE playlistid = ?)";
            } else {
                sql = "SELECT * FROM tracks LEFT JOIN playlist_tracks pt ON tracks.id = pt.trackId WHERE pt.playlistId = ?";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, forPlaylist);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Track> tracks = new ArrayList<>();

            while (resultSet.next()) {
                Track track = new Track();
                track.setId(resultSet.getInt("id"));
                track.setTitle(resultSet.getString("title"));
                track.setPerformer(resultSet.getString("performer"));
                track.setDuration(resultSet.getInt("duration"));
                track.setAlbum(resultSet.getString("album"));
                track.setPlaycount(resultSet.getInt("playcount"));
                track.setPublicationDate(resultSet.getString("publicationDate"));
                track.setDescription(resultSet.getString("description"));
                track.setOfflineAvailable(resultSet.getBoolean("offlineAvailable"));

                tracks.add(track);
            }

            return tracks;
        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            throw new InternalServerErrorException();
        }
    }

    @Override
    public void removeTrackFromPlaylist(int playlistId, int trackId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM playlist_tracks WHERE playlistId = ? AND trackId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            throw new InternalServerErrorException();
        }
    }

    @Override
    public void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO playlist_tracks (playlistId, trackId, offlineAvailable) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);
            preparedStatement.setBoolean(3, offlineAvailable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe(e.toString());
            throw new InternalServerErrorException();
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}