package oose.dea.dao;

import oose.dea.dto.AddPlaylistRequestDTO;
import oose.dea.dto.PlaylistsDTO;

public interface IPlaylistDAO {

    /**
     *
     * @param token user token
     * @return all playlists
     * @link https://github.com/aaron5670/SpotiTube#get-all-playlists
     */
    PlaylistsDTO getAllPlaylists(String token);

    /**
     *
     * @param id the id of a playlist
     * @param token valid user token
     * @return returns all playlists except the deleted one
     * @link https://github.com/aaron5670/SpotiTube#delete-a-playlist
     */
    PlaylistsDTO deleteAPlaylist(int id, String token);

    /**
     *
     * @param addPlaylistRequestDTO addPlaylistRequest DTO
     * @param token valid user token
     * @return returns the body of the created playlist
     * @link https://github.com/aaron5670/SpotiTube#add-a-playlist
     */
    PlaylistsDTO addAPlaylist(AddPlaylistRequestDTO addPlaylistRequestDTO, String token);
}
