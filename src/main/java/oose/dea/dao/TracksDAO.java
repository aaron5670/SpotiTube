package oose.dea.dao;

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
public class TracksDAO implements IPlaylistDAO {

    private TokenService tokenService;

    @Resource(name = "jdbc/spotitubeMySQL")
    DataSource dataSource;

    @Override
    public PlaylistsDTO getAllPlaylists(String token) {
        return null;
    }

    @Override
    public PlaylistsDTO deleteAPlaylist(int id, String token) {
        return null;
    }

    @Override
    public PlaylistsDTO addAPlaylist(PlaylistDTO playlistDTO, String token) {
        return null;
    }

    @Override
    public PlaylistsDTO editAPlaylist(PlaylistDTO playlistDTO, String token) {
        return null;
    }

    @Inject
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }
}