package oose.dea.dao;

import oose.dea.controller.dto.TrackDTO;
import oose.dea.domain.Track;
import oose.dea.service.TokenService;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Default
public class TrackDAO implements ITrackDAO {

    private TokenService tokenService;

    @Resource(name = "jdbc/spotitubeMySQL")
    DataSource dataSource;

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
            e.printStackTrace();
            return null;
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    @Inject
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }
}