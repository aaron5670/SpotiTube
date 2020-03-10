package oose.dea.controller;

import oose.dea.dao.IPlaylistDAO;
import oose.dea.dto.PlaylistsDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class PlaylistController {

    private IPlaylistDAO iPlaylistDAO;

    @GET
    @Path("/playlists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists() {

        //ToDO:
        //  Find out how to get the current user token?
        String token = "USER-TOKEN";

        PlaylistsDTO playlists = iPlaylistDAO.getAllPlaylists(token);

        if (playlists == null) {
            return Response.status(404).build();
        }

        return Response.status(200).entity(playlists).build();
    }

    @Inject
    public void setiPlaylistDAO(IPlaylistDAO iPlaylistDAO) {
        this.iPlaylistDAO = iPlaylistDAO;
    }
}
