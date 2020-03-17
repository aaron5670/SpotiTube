package oose.dea.dao;

import oose.dea.dto.AddPlaylistRequestDTO;
import oose.dea.dto.PlaylistDTO;
import oose.dea.dto.PlaylistsDTO;
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
public class PlaylistDAO implements IPlaylistDAO {

    private TokenService tokenService;
    private PlaylistsDTO playlistsDTO = new PlaylistsDTO();

    @Resource(name = "jdbc/spotitubeMySQL")
    DataSource dataSource;

    @Override
    public PlaylistsDTO getAllPlaylists(String token) {
        if (tokenService.verifyToken(token)) {
            ArrayList<PlaylistDTO> playlists = new ArrayList<>();

            try (Connection connection = dataSource.getConnection()) {
                String sql = "SELECT p.id, p.name, p.owner, t.token FROM playlists p INNER JOIN users u on p.owner = u.username LEFT OUTER JOIN tokens t on u.username = t.username";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    PlaylistDTO playlist = new PlaylistDTO();
                    playlist.setId(resultSet.getInt("p.id"));
                    playlist.setName(resultSet.getString("p.name"));

                    if (resultSet.getString("t.token") != null && resultSet.getString("t.token").equals(token)) {
                        playlist.setOwner(true);
                    } else {
                        playlist.setOwner(false);
                    }

                    //ToDo:
                    //  Add tracks

                    playlists.add(playlist);
                }

                //ToDo:
                //  Calculate total length in seconds
                playlistsDTO.setLength(100);

                playlistsDTO.setPlaylists(playlists);

                return playlistsDTO;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public PlaylistsDTO deleteAPlaylist(int id, String token) {
        if (tokenService.verifyToken(token)) {
            try (Connection connection = dataSource.getConnection()) {
                String sql = "DELETE FROM playlists WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

                return getAllPlaylists(token);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public PlaylistsDTO addAPlaylist(AddPlaylistRequestDTO addPlaylistRequestDTO, String token) {
        return null;
    }

    @Inject
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }
}