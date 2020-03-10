package oose.dea.dto;

import java.util.ArrayList;

public class PlaylistsDTO {
    private ArrayList<PlaylistDTO> playlists = new ArrayList<>();
    private int length;

    public PlaylistsDTO(int length) {
        this.length = length;
    }

    public ArrayList<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
