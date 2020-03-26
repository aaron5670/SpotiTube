package oose.dea.controller;

import oose.dea.controller.dto.TrackDTO;
import oose.dea.dao.ITrackDAO;
import oose.dea.controller.dto.TracksDTO;
import oose.dea.domain.Track;
import oose.dea.service.TokenService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class TracksController {

    @Inject
    private TokenService tokenService;

    @Inject
    private ITrackDAO iTrackDAO;

    @GET
    @Path("/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracks(@QueryParam("forPlaylist") int playlistId, @QueryParam("token") String token) {
        if (!tokenService.tokenVerified(token)) throw new ForbiddenException("Invalid user token");

        return Response.status(200).entity(tracksDTO(playlistId, true, token)).build();
    }

    @GET
    @Path("/playlists/{playlistId}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksFromPlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token) {
        if (!tokenService.tokenVerified(token)) throw new ForbiddenException("Invalid user token");

        return Response.status(200).entity(tracksDTO(playlistId, false, token)).build();
    }

    @DELETE
    @Path("/playlists/{playlistId}/tracks/{trackId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId, @QueryParam("token") String token) {
        if (!tokenService.tokenVerified(token)) throw new ForbiddenException("Invalid user token");

        iTrackDAO.removeTrackFromPlaylist(playlistId, trackId);
        return Response.status(200).entity(tracksDTO(playlistId, false, token)).build();
    }

    @POST
    @Path("/playlists/{playlistId}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("playlistId") int playlistId, TrackDTO trackDTO, @QueryParam("token") String token) {
        if (!tokenService.tokenVerified(token)) throw new ForbiddenException("Invalid user token");

        iTrackDAO.addTrackToPlaylist(playlistId, trackDTO.id, trackDTO.offlineAvailable);
        return Response.status(201).entity(tracksDTO(playlistId, false, token)).build();
    }

    public TracksDTO tracksDTO(int forPlaylist, boolean addTracks, String token) {
        List<Track> tracks = iTrackDAO.getAllTracks(forPlaylist, addTracks, token);
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = new ArrayList<>();

        for (Track track : tracks) {
            TrackDTO trackDTO = new TrackDTO();
            trackDTO.id = track.getId();
            trackDTO.title = track.getTitle();
            trackDTO.performer = track.getPerformer();
            trackDTO.duration = track.getDuration();
            trackDTO.album = track.getAlbum();
            trackDTO.playcount = track.getPlaycount();
            trackDTO.publicationDate = track.getPublicationDate();
            trackDTO.description = track.getDescription();
            trackDTO.offlineAvailable = track.getOfflineAvailable();

            tracksDTO.tracks.add(trackDTO);
        }
        return tracksDTO;
    }
}
