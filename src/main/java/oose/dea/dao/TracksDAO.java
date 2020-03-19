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
public class TracksDAO implements ITracksDAO {

    private TokenService tokenService;

    @Resource(name = "jdbc/spotitubeMySQL")
    DataSource dataSource;

    @Override
    public List<Track> getAllTracks(String forPlaylist, String token) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement;
            if (forPlaylist != null) {
                String sql = "SELECT * FROM tracks WHERE playlistId = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, forPlaylist);
            } else {
                String sql = "SELECT * FROM tracks";
                preparedStatement = connection.prepareStatement(sql);
            }
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
    public TrackDTO getTracksFromPlaylist(String token) {
        return null;
    }

    @Override
    public TrackDTO removeTrackFromPlaylist(String token) {
        return null;
    }

    @Override
    public TrackDTO addTrackToPlaylist(String token) {
        return null;
    }

    @Inject
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }
}