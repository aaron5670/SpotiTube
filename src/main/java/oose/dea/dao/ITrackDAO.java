package oose.dea.dao;

import oose.dea.controller.dto.TrackDTO;
import oose.dea.domain.Track;

import java.util.List;

public interface ITrackDAO {

    /**
     *
     * @param forPlaylist valid playlist
     * @param token valid user token
     * @return a response containing the complete list of available tracks
     * @link https://github.com/aaron5670/SpotiTube#get-all-tracks
     */
    List<Track> getAllTracks(int forPlaylist, boolean addTracks, String token);

    /**
     *
     * @param token valid user token
     * @return a response containing the complete and modified list of tracks
     * @link https://github.com/aaron5670/SpotiTube#remove-a-track-from-a-playlist
     */
    TrackDTO removeTrackFromPlaylist(String token);

    /**
     *
     * @param token valid user token
     * @return a response containing the complete list of tracks for the given
     * @link https://github.com/aaron5670/SpotiTube#add-a-track-to-a-playlist
     */
    TrackDTO addTrackToPlaylist(String token);
}
