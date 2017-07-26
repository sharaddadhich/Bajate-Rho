package com.example.manoj.musicplayer;

/**
 * Created by Manoj on 7/24/2017.
 */

public class SongsList {

    String Title,Artist,songPath,Albumname, seclength;
    Integer ArtistId,AlbumId;
    Integer minlength;

    public SongsList(String title, String artist, String songPath, String albumname, Integer artistId, Integer albumId,
                     int min,String sec) {
        Title = title;
        Artist = artist;
        this.songPath = songPath;
        Albumname = albumname;
        ArtistId = artistId;
        AlbumId = albumId;
        this.minlength =min;
        this.seclength = sec;
    }

    public Integer getMinlength() {
        return minlength;
    }

    public void setMinlength(Integer minlength) {
        this.minlength = minlength;
    }

    public String getSeclength() {
        return seclength;
    }

    public void setSeclength(String seclength) {
        this.seclength = seclength;
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
