package oose.dea.dao;

import oose.dea.dto.PlaylistsDTO;

public interface IPlaylistDAO {

    PlaylistsDTO getAllPlaylists(String token);
}
