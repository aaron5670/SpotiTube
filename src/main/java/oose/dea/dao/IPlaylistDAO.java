package oose.dea.dao;

import oose.dea.dto.PlaylistsDTO;

public interface IPlaylistDAO {

    /**
     *
     * @param token user token
     * @return all playlists
     */
    PlaylistsDTO getAllPlaylists(String token);

    /**
     *
     * @param id the id of a playlist
     * @param token valid user token
     * @return returns all playlist except the deleted one
     */
    PlaylistsDTO deleteAPlaylist(int id, String token);
}
