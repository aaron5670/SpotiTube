package oose.dea.controller;

import oose.dea.controller.dto.TrackDTO;
import oose.dea.dao.ITracksDAO;
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

    private TokenService tokenService;
    private ITracksDAO iTracksDAO;

    @GET
    @Path("/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") String forPlaylist) {
        try {
            if (!tokenService.tokenVerified(token))
                return Response.status(400).build();

            return Response.status(200).entity(tracksDTO(forPlaylist, token)).build();
        } catch (InternalServerErrorException e) {
            e.getStackTrace();
            return Response.status(500).build();
        }
    }

    public TracksDTO tracksDTO(String forPlaylist, String token) throws InternalServerErrorException {
        List<Track> tracks = iTracksDAO.getAllTracks(forPlaylist, token);
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = new ArrayList<>();

        for (Track track : tracks) {
            TrackDTO trackDTO = new TrackDTO();
            trackDTO.id = track.getId();
        }

        return tracksDTO;
    }

    @Inject
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Inject
    public void setiTracksDAO(ITracksDAO iTracksDAO) {
        this.iTracksDAO = iTracksDAO;
    }
}
