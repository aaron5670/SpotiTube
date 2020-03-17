package oose.dea.controller;

import oose.dea.dao.ITracksDAO;
import oose.dea.dto.PlaylistsDTO;
import oose.dea.dto.TrackDTO;
import oose.dea.dto.TracksDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class TracksController {

    private ITracksDAO iTracksDAO;

    @GET
    @Path("/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracks(@Context UriInfo QueryParam) {
        String forPlaylist = QueryParam.getQueryParameters().getFirst("forPlaylist");
        String token = QueryParam.getQueryParameters().getFirst("token");
        TracksDTO tracks = iTracksDAO.getAllTracks(forPlaylist, token);

        if (tracks == null) return Response.status(404).build();

        return Response.status(200).entity(tracks).build();
    }

    @Inject
    public void setiTracksDAO(ITracksDAO iTracksDAO) {
        this.iTracksDAO = iTracksDAO;
    }
}
