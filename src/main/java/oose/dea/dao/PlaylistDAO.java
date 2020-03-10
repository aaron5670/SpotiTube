package oose.dea.dao;

import oose.dea.dto.PlaylistsDTO;
import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;

@Default
public class PlaylistDAO implements IPlaylistDAO {

    @Resource(name = "jdbc/spotitubeMySQL")
    DataSource dataSource;

    @Override
    public PlaylistsDTO getAllPlaylists(String token) {
        //ToDo:
        //  First verify if the token is real.
        //  -- First create a token database table with a relation to users.                <--- DONE!
        //  -- Need a new service class to check if this token exists in the database.
        //  -- -- Need a TokenDAO too properly...?
        //  -- If token is verified, get all Playlists from database
        return null;
    }
}