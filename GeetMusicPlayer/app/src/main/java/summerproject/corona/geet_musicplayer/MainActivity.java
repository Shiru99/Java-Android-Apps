package summerproject.corona.geet_musicplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button musicbutton;
    private Button videobutton;
    private Button shufflebutton;
    private ListView listView;
    private boolean toggle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("AppLogs", "App Started");

        musicbutton = findViewById(R.id.musicbutton);
        videobutton = findViewById(R.id.videobutton);
        shufflebutton = findViewById(R.id.shufflebutton);
        listView = findViewById(R.id.listView);

        Log.d("AppLogs", "App asking for permission");
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();

                        Song.SongFiles = fetchMusic(Environment.getExternalStorageDirectory());

                        Song.songPlayList = new Song[Song.SongFiles.size()];

                        for (int i = 0; i < Song.SongFiles.size(); i++) {
                            Song temp = new Song();
                            Song.songPlayList[i] = temp;
                            try {
                                metaRetriver.setDataSource(String.valueOf(Song.SongFiles.get(i)));

                                Song.songPlayList[i].setAlbumArt(metaRetriver.getEmbeddedPicture());

                                Song.songPlayList[i].setSongAlbum(
                                        metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));

                                if (Song.songPlayList[i].getSongAlbum() == null) {
                                    Song.songPlayList[i].setSongAlbum("Unknown Album");
                                }

                                Song.songPlayList[i].setSongArtist(
                                        metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                                if (Song.songPlayList[i].getSongArtist() == null) {
                                    Song.songPlayList[i].setSongArtist("Unknown Artist");
                                }

                            } catch (Exception e) {
                                Song.songPlayList[i].setSongAlbum("Unknown Album");
                                Song.songPlayList[i].setSongArtist("Unknown Artist");
                            }

                            Song.songPlayList[i].setSongNames(Song.SongFiles.get(i).getName().replace(".mp3", ""));
                        }

                        Log.d("AppLogs", "Fetched Songs :" + Song.songPlayList.length);

                        CustomAdapter adapter = new CustomAdapter(MainActivity.this, R.layout.custom_listview_layout,
                                Song.songPlayList);
                        listView.setAdapter(adapter);
                        listView.setDivider(getDrawable(R.drawable.divider));
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this, "Need Storage Access to work", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest,
                            PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

        musicbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v)
            {

                Log.d("AppLogs", "Request for Music");
                toggle = true;
                musicbutton.setTextColor(Color.parseColor("#FF8219"));
                videobutton.setTextColor(Color.parseColor("#FFFFFF"));
                // shufflebutton.setVisibility(View.VISIBLE);
                Log.d("AppLogs", "Fetched Songs :" + Song.songPlayList.length);
                CustomAdapter adapter = new CustomAdapter(MainActivity.this, R.layout.custom_listview_layout,
                        Song.songPlayList);
                listView.setAdapter(adapter);
                listView.setDivider(getDrawable(R.drawable.divider));

            }
        });

        videobutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Log.d("AppLogs", "Request for Video");
                toggle = false;
                videobutton.setTextColor(Color.parseColor("#FF8219"));
                musicbutton.setTextColor(Color.parseColor("#FFFFFF"));
                // shufflebutton.setVisibility(View.GONE);

                if (Video.videoPlayList == null) {
                    MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();

                    Video.videoFiles = fetchVideos(Environment.getExternalStorageDirectory());

                    Video.videoPlayList = new Video[Video.videoFiles.size()];

                    for (int i = 0; i < Video.videoFiles.size(); i++) {
                        Video temp = new Video();
                        Video.videoPlayList[i] = temp;
                        try {
                            metaRetriver.setDataSource(String.valueOf(Video.videoFiles.get(i)));
                            Video.videoPlayList[i].setVideoAlbum(
                                    metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));

                            Video.videoPlayList[i].setVideoArtist(
                                    metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));

                        } catch (Exception e) {
                            Video.videoPlayList[i].setVideoAlbum("Unknown Album");
                            Video.videoPlayList[i].setVideoArtist("Unknown Artist");
                        }
                        Video.videoPlayList[i].setVideoNames(Video.videoFiles.get(i).getName().replace(".mp3", ""));
                    }
                }

                Log.d("AppLogs", "Fetched Videos :" + Video.videoPlayList.length);

                CustomAdapterVideo adapter = new CustomAdapterVideo(MainActivity.this, R.layout.custom_listview_layout,
                        Video.videoPlayList);
                listView.setAdapter(adapter);
                listView.setDivider(getDrawable(R.drawable.divider));

            }
        });

        shufflebutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Log.d("AppLogs", "Request for Shuffle Music");
                Toast.makeText(MainActivity.this, "Enjoy!", Toast.LENGTH_SHORT).show();
                Random rand = new Random();
                if(toggle){
                    int totalSongs = Song.songPlayList.length;
                    int position = rand.nextInt(totalSongs) ;

                    Log.d("AppLogs", "Playing Song : "+position+" - "+Song.songPlayList[position].getSongNames());
                    final Intent intent_MusicPlayer = new Intent(MainActivity.this, MusicPlayer.class);
                    intent_MusicPlayer.putExtra("position", position);
                    startActivity(intent_MusicPlayer);

                }else{
                    int totalVideos = Video.videoPlayList.length;
                    int position = rand.nextInt(totalVideos) ;

                    Log.d("AppLogs", "Playing video : "+position+" - "+Video.videoPlayList[position].getVideoNames());
                    final Intent intent_VideoPlayer = new Intent(MainActivity.this, VideoPlayer.class);
                    intent_VideoPlayer.putExtra("position", position);
                    startActivity(intent_VideoPlayer);
                }


            }
        });

    }

    public ArrayList<File> fetchMusic(File file) {
        ArrayList songList = new ArrayList();

        File[] files = file.listFiles();

        if (files != null) {
            for (File eachFile : files) {
                if (!eachFile.isHidden() && eachFile.isDirectory()) {
                    songList.addAll(fetchMusic(eachFile));
                } else {
                    if (eachFile.getName().endsWith(".mp3") && !eachFile.getName().startsWith(".")) {
                        songList.add(eachFile);
                    }
                }
            }
        }
        return songList;
    }

    public ArrayList<File> fetchVideos(File file) {
        ArrayList videoList = new ArrayList();

        File[] files = file.listFiles();

        if (files != null) {
            for (File eachFile : files) {
                if (!eachFile.isHidden() && eachFile.isDirectory()) {
                    videoList.addAll(fetchVideos(eachFile));
                } else if (eachFile.getName().endsWith(".mp4") && !eachFile.getName().startsWith(".")) {
                    videoList.add(eachFile);
                }
            }
        }
        return videoList;
    }
}