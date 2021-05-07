package summerproject.corona.geet_musicplayer;

public class Song {
    private String songNames;
    private  String songArtist;
    private String songAlbum;

    public Song() {

    }

    public Song(String songNames, String songArtist, String songAlbum) {
        this.songNames = songNames;
        this.songArtist = songArtist;
        this.songAlbum = songAlbum;
    }

    public String getSongNames() {
        return songNames;
    }

    public void setSongNames(String songNames) {
        this.songNames = songNames;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }
}
