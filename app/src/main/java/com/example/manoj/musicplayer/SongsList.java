package com.example.manoj.musicplayer;

/**
 * Created by Manoj on 7/24/2017.
 */

public class SongsList {

    String Title,Artist,songPath,Albumname;
    Integer ArtistId,AlbumId;
    Integer Duration;

    public SongsList(String title, String artist, String songPath, String albumname, Integer artistId, Integer albumId,
                     Integer Duration) {
        Title = title;
        Artist = artist;
        this.songPath = songPath;
        Albumname = albumname;
        ArtistId = artistId;
        AlbumId = albumId;
        this.Duration = Duration;
    }

    public Integer getDuration() {
        return Duration;
    }

    public void setDuration(Integer duration) {
        Duration = duration;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public String getAlbumname() {
        return Albumname;
    }

    public void setAlbumname(String albumname) {
        Albumname = albumname;
    }

    public Integer getArtistId() {
        return ArtistId;
    }

    public void setArtistId(Integer artistId) {
        ArtistId = artistId;
    }

    public Integer getAlbumId() {
        return AlbumId;
    }

    public void setAlbumId(Integer albumId) {
        AlbumId = albumId;
    }
}
