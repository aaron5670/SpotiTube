package oose.dea.controller;

import oose.dea.dao.IPlaylistDAO;
import oose.dea.dto.AddPlaylistRequestDTO;
import oose.dea.dto.PlaylistsDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class PlaylistController {

    private IPlaylistDAO iPlaylistDAO;

    @GET
    @Path("/playlists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@Context UriInfo QueryParam) {
        String token = QueryParam.getQueryParameters().getFirst("token");

        PlaylistsDTO playlists = iPlaylistDAO.getAllPlaylists(token);

        if (playlists == null) {
            System.out.println("isNull");
            return Response.status(404).build();
        }

        return Response.status(200).entity(playlists).build();
    }

    @DELETE
    @Path("/playlists/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAPlaylist(@PathParam("id") int id, @Context UriInfo QueryParam) {
        String token = QueryParam.getQueryParameters().getFirst("token");

        PlaylistsDTO playlists = iPlaylistDAO.deleteAPlaylist(id, token);

        if (playlists == null) {
            System.out.println("isNull");
            return Response.status(404).build();
        }

        return Response.status(200).entity(playlists).build();
    }

    @POST
    @Path("/playlists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAPlaylist(AddPlaylistRequestDTO addPlaylistRequestDTO, @Context UriInfo QueryParam) {
        String token = QueryParam.getQueryParameters().getFirst("token");

        PlaylistsDTO playlists = iPlaylistDAO.addAPlaylist(addPlaylistRequestDTO, token);

        if (playlists == null) {
            System.out.println("isNull");
            return Response.status(404).build();
        }

        return Response.status(200).entity(playlists).build();
    }

    @Inject
    public void setiPlaylistDAO(IPlaylistDAO iPlaylistDAO) {
        this.iPlaylistDAO = iPlaylistDAO;
    }
}
