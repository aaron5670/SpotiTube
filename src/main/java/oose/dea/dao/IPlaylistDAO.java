package oose.dea.dao;

import oose.dea.domain.Playlist;

import java.util.List;

public interface IPlaylistDAO {

    /**
     *
     * @param token valid user token
     * @return a response containing the complete list of playlists
     * @link https://github.com/aaron5670/SpotiTube#get-all-playlists
     */
    List<Playlist> getAllPlaylists(String token);

    /**
     *
     * @param id the id of a playlist
     * @param token valid user token
     * @link https://github.com/aaron5670/SpotiTube#delete-a-playlist
     */
    void deleteAPlaylist(int id, String token);

    /**
     *
     * @param token valid user token
     * @link https://github.com/aaron5670/SpotiTube#add-a-playlist
     */
    void addAPlaylist(String playlistName, String username, String token);

    /**
     *
     * @param token valid user token
     * @link https://github.com/aaron5670/SpotiTube#edit-a-playlist
     */
    void editAPlaylist(String playlistName, int playlistId, String token);
}
