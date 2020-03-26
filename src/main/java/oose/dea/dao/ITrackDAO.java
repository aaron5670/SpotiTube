package oose.dea.dao;

import oose.dea.domain.Track;

import java.util.List;

public interface ITrackDAO {

    /**
     * @param forPlaylist valid playlist
     * @param token       valid user token
     * @return a response containing the complete list of available tracks
     * @link https://github.com/aaron5670/SpotiTube#get-all-tracks
     */
    List<Track> getAllTracks(int forPlaylist, boolean addTracks, String token);

    /**
     * @param playlistId valid playlistId
     * @param trackId    valid trackId
     * @link https://github.com/aaron5670/SpotiTube#remove-a-track-from-a-playlist
     */
    void removeTrackFromPlaylist(int playlistId, int trackId);

    /**
     * @param playlistId       valid playlistId
     * @param trackId          valid trackId
     * @param offlineAvailable valid offlineAvailable
     * @link https://github.com/aaron5670/SpotiTube#add-a-track-to-a-playlist
     */
    void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable);
}
