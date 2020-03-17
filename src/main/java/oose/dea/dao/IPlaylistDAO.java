package oose.dea.dao;

import oose.dea.dto.PlaylistDTO;
import oose.dea.dto.PlaylistsDTO;

public interface IPlaylistDAO {

    /**
     *
     * @param token valid user token
     * @return a response containing the complete list of playlists
     * @link https://github.com/aaron5670/SpotiTube#get-all-playlists
     */
    PlaylistsDTO getAllPlaylists(String token);

    /**
     *
     * @param id the id of a playlist
     * @param token valid user token
     * @return a response containing the complete and modified list of playlists
     * @link https://github.com/aaron5670/SpotiTube#delete-a-playlist
     */
    PlaylistsDTO deleteAPlaylist(int id, String token);

    /**
     *
     * @param playlistDTO playlist DTO
     * @param token valid user token
     * @return a response containing the complete and modified list of playlists
     * @link https://github.com/aaron5670/SpotiTube#add-a-playlist
     */
    PlaylistsDTO addAPlaylist(PlaylistDTO playlistDTO, String token);

    /**
     *
     * @param playlistDTO playlist DTO
     * @param token valid user token
     * @return return a response containing the complete and modified list of playlists
     * @link https://github.com/aaron5670/SpotiTube#edit-a-playlist
     */
    PlaylistsDTO editAPlaylist(PlaylistDTO playlistDTO, String token);
}
