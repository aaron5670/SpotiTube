package oose.dea.controller;

import oose.dea.dao.ITracksDAO;
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
    public Response getAllPlaylists(@Context UriInfo QueryParam) {
        String token = QueryParam.getQueryParameters().getFirst("token");
        return Response.status(200).entity("tracks").build();
    }

    @Inject
    public void setiTracksDAO(ITracksDAO iTracksDAO) {
        this.iTracksDAO = iTracksDAO;
    }
}
