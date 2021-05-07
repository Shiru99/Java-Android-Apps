package summerproject.corona.geet_musicplayer;

public class Video {

    public Video() {

    }


    private String videoNames;
    private  String videoArtist;
    private String videoAlbum;

    public Video(String videoNames, String videoArtist, String videoAlbum) {
        this.videoNames = videoNames;
        this.videoArtist = videoArtist;
        this.videoAlbum = videoAlbum;
    }

    public String getVideoNames() {
        return videoNames;
    }

    public void setVideoNames(String videoNames) {
        this.videoNames = videoNames;
    }

    public String getVideoArtist() {
        return videoArtist;
    }

    public void setVideoArtist(String videoArtist) {
        this.videoArtist = videoArtist;
    }

    public String getVideoAlbum() {
        return videoAlbum;
    }

    public void setVideoAlbum(String videoAlbum) {
        this.videoAlbum = videoAlbum;
    }
}
