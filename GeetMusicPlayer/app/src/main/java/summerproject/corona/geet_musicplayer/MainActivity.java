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

public class MainActivity extends AppCompatActivity {

    public static String EXTRA_MusicPlayer = "summerproject.corona.geet_musicplayer.extra.musicplayer"; // Unique
    public static String EXTRA_VideoPlayer = "summerproject.corona.geet_musicplayer.extra.videoplayer"; // Unique
    private Button musicbutton;
    private Button videobutton;
    private Button shufflebutton;
    private ListView listView;

    private Song[] songPlayList;
    private Video[] videoPlayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("AppLogs", "App Started");

        final Intent intent_MusicPlayer = new Intent(this, MusicPlayer.class);
        final Intent intent_VideoPlayer = new Intent(this, VideoPlayer.class);

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

                        ArrayList<File> Songs = fetchMusic(Environment.getExternalStorageDirectory());

                        songPlayList = new Song[Songs.size()];

                        for (int i = 0; i < Songs.size(); i++) {
                            Song temp = new Song();
                            songPlayList[i] = temp;
                            try {
                                metaRetriver.setDataSource(String.valueOf(Songs.get(i)));

                                songPlayList[i].setSongAlbum(
                                        metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));

                                if (songPlayList[i].getSongAlbum() == null) {
                                    songPlayList[i].setSongAlbum("Unknown Album");
                                }

                                songPlayList[i].setSongArtist(
                                        metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                                if (songPlayList[i].getSongArtist() == null) {
                                    songPlayList[i].setSongArtist("Unknown Artist");
                                }

                            } catch (Exception e) {
                                songPlayList[i].setSongAlbum("Unknown Album");
                                songPlayList[i].setSongArtist("Unknown Artist");
                            }

                            songPlayList[i].setSongNames(Songs.get(i).getName().replace(".mp3", ""));
                        }

                        Log.d("AppLogs", "Fetched Songs :" + songPlayList.length);

                        CustomAdapter adapter = new CustomAdapter(MainActivity.this, R.layout.custom_listview_layout,
                                songPlayList);
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
            public void onClick(View v) {
                Log.d("AppLogs", "Request for Music");
                musicbutton.setTextColor(Color.parseColor("#FF8219"));
                videobutton.setTextColor(Color.parseColor("#FFFFFF"));
                // shufflebutton.setVisibility(View.VISIBLE);
                Log.d("AppLogs", "Fetched Songs :" + songPlayList.length);
                CustomAdapter adapter = new CustomAdapter(MainActivity.this, R.layout.custom_listview_layout,
                        songPlayList);
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
                // Toast.makeText(MainActivity.this, "All The Best", Toast.LENGTH_SHORT).show();
                videobutton.setTextColor(Color.parseColor("#FF8219"));
                musicbutton.setTextColor(Color.parseColor("#FFFFFF"));
                // shufflebutton.setVisibility(View.GONE);

                if (videoPlayList == null) {
                    MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();

                    ArrayList<File> Videos = fetchVideos(Environment.getExternalStorageDirectory());

                    videoPlayList = new Video[Videos.size()];

                    for (int i = 0; i < Videos.size(); i++) {
                        Video temp = new Video();
                        videoPlayList[i] = temp;
                        try {
                            metaRetriver.setDataSource(String.valueOf(Videos.get(i)));
                            videoPlayList[i].setVideoAlbum(
                                    metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));

                            videoPlayList[i].setVideoArtist(
                                    metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));

                        } catch (Exception e) {
                            videoPlayList[i].setVideoAlbum("Unknown Album");
                            videoPlayList[i].setVideoArtist("Unknown Artist");
                        }
                        videoPlayList[i].setVideoNames(Videos.get(i).getName().replace(".mp3", ""));
                    }
                }

                Log.d("AppLogs", "Fetched Videos :" + videoPlayList.length);

                CustomAdapterVideo adapter = new CustomAdapterVideo(MainActivity.this, R.layout.custom_listview_layout,
                        videoPlayList);
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
                musicbutton.setTextColor(Color.parseColor("#FF8219"));
                videobutton.setTextColor(Color.parseColor("#FFFFFF"));
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