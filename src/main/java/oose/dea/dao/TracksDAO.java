package oose.dea.dao;

import oose.dea.controller.dto.TrackDTO;
import oose.dea.controller.dto.TracksDTO;
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

@Default
public class TracksDAO implements ITracksDAO {

    private TokenService tokenService;
    private TracksDTO tracksDTO = new TracksDTO();

    @Resource(name = "jdbc/spotitubeMySQL")
    DataSource dataSource;

    @Override
    public TracksDTO getAllTracks(String forPlaylist, String token) {
        if (tokenService.tokenVerified(token)) {
            ArrayList<TrackDTO> tracks = new ArrayList<>();

            try (Connection connection = dataSource.getConnection()) {

                PreparedStatement preparedStatement;
                if (forPlaylist != null) {
                    String sql = "SELECT * FROM tracks WHERE playlistId = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, forPlaylist);
                }else {
                    String sql = "SELECT * FROM tracks";
                    preparedStatement = connection.prepareStatement(sql);
                }
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    TrackDTO track = new TrackDTO();
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

                tracksDTO.setTracks(tracks);

                return tracksDTO;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
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