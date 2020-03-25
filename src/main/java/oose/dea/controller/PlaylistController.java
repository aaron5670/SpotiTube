package oose.dea.controller;

import oose.dea.dao.IPlaylistDAO;
import oose.dea.controller.dto.PlaylistDTO;
import oose.dea.controller.dto.PlaylistsDTO;
import oose.dea.domain.Playlist;
import oose.dea.service.TokenService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class PlaylistController {

    private TokenService tokenService;
    private IPlaylistDAO iPlaylistDAO;

    @GET
    @Path("/playlists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        if (!tokenService.tokenVerified(token))
            return Response.status(403).build();

        return Response.status(200).entity(playlistsDTO(token)).build();
    }

    @DELETE
    @Path("/playlists/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAPlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        if (!tokenService.tokenVerified(token))
            return Response.status(403).build();

        iPlaylistDAO.deleteAPlaylist(id, token);
        return Response.status(200).entity(playlistsDTO(token)).build();
    }

    @POST
    @Path("/playlists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAPlaylist(PlaylistDTO playlistDTO, @QueryParam("token") String token) {
        if (!tokenService.tokenVerified(token))
            return Response.status(403).build();

        iPlaylistDAO.addAPlaylist(playlistDTO.name, tokenService.getUsernameByToken(token), token);
        return Response.status(201).entity(playlistsDTO(token)).build();
    }

    @PUT
    @Path("/playlists/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editAPlaylist(PlaylistDTO playlistDTO, @QueryParam("token") String token) {
        if (!tokenService.tokenVerified(token))
            return Response.status(403).build();

        iPlaylistDAO.editAPlaylist(playlistDTO.name, playlistDTO.id, token);
        return Response.status(200).entity(playlistsDTO(token)).build();
    }

    public PlaylistsDTO playlistsDTO(String token) {
        List<Playlist> playlists = iPlaylistDAO.getAllPlaylists(token);
        PlaylistsDTO playlistsDTO = new PlaylistsDTO();
        playlistsDTO.playlists = new ArrayList<>();

        int length = 0;
        for (Playlist playlist : playlists) {
            PlaylistDTO playlistDTO = new PlaylistDTO();
            playlistDTO.id = playlist.getId();
            playlistDTO.name = playlist.getName();
            playlistDTO.owner = playlist.isOwner();
            playlistsDTO.playlists.add(playlistDTO);
            length += playlist.getTotalDuration();
        }

        playlistsDTO.length = length;
        return playlistsDTO;
    }

    @Inject
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Inject
    public void setiPlaylistDAO(IPlaylistDAO iPlaylistDAO) {
        this.iPlaylistDAO = iPlaylistDAO;
    }
}
