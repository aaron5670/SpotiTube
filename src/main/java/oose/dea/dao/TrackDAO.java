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
            String sql = (addTracks) ?
                    "SELECT * FROM playlist_tracks INNER JOIN tracks ON playlist_tracks.trackId = tracks.id WHERE playlistId != ?"
                    :
                    "SELECT * FROM playlist_tracks INNER JOIN tracks ON playlist_tracks.trackId = tracks.id WHERE playlistId = ?";
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