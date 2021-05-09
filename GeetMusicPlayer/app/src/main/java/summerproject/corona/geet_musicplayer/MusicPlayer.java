package summerproject.corona.geet_musicplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

public class MusicPlayer extends AppCompatActivity {

    private ImageView albumArt,albumArtBlur;
    private TextView songName,songDescription;
    private MediaPlayer mediaPlayer;
    private ImageButton backward,previous, playpause, next, forward;
    private TextView timeSpent,timeRemaining;
    private SeekBar seekBar;
    private Thread updateSeek;

    int position;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateSeek.interrupt();
        mediaPlayer.stop();
        mediaPlayer.release();
        Log.d("AppLogs","onBackPressed");
    }

    @SuppressLint({"WrongConstant", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        setContentView(R.layout.activity_music_player);

        seekBar = findViewById(R.id.seekBar);
        backward = findViewById(R.id.backward);
        previous = findViewById(R.id.previous);
        playpause = findViewById(R.id.playpause);
        next = findViewById(R.id.next);
        forward = findViewById(R.id.forward);

        Log.d("AppLogs", "Entered in Music Player");
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);


        Uri uri = Uri.parse(Song.SongFiles.get(position).toString());
        mediaPlayer = MediaPlayer.create(this, uri);
        mediaPlayer.start();
        playpause.setImageResource(R.drawable.pause);
        Log.d("AppLogs", "Song Started Playing");

        // Song Details Display
        setSongDetails(position);

        // TODO : SeekBar
        seekBar.setMax(mediaPlayer.getDuration());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String time = timeFormat(mediaPlayer.getCurrentPosition());
                timeSpent.setText(time);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                String time = timeFormat(mediaPlayer.getCurrentPosition());
                timeSpent.setText(time);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
//                String time = timeFormat(mediaPlayer.getCurrentPosition());
//                timeSpent.setText(time);
            }
        });

        updateSeek = new Thread(){
            @Override
            public void run() {

                String time = timeFormat(mediaPlayer.getCurrentPosition());
                timeSpent.setText(time);

                int currentPosition = 0;
                try{
                    while(currentPosition<mediaPlayer.getDuration()){
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        sleep(500);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        updateSeek.start();




        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String time = timeFormat(mediaPlayer.getCurrentPosition());
                Log.d("AppLogs", time);

                if(mediaPlayer.getCurrentPosition()>5000){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);
                }
                else{
                    mediaPlayer.seekTo(0);
                }

                time = timeFormat(mediaPlayer.getCurrentPosition());
                Log.d("AppLogs", time);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position!=0){
                    position = position - 1;
                }
                else{
                    position = Song.SongFiles.size() - 1;
                }
                Uri uri = Uri.parse(Song.SongFiles.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
                playpause.setImageResource(R.drawable.pause);
                seekBar.setMax(mediaPlayer.getDuration());
                setSongDetails(position);

                String time = timeFormat(mediaPlayer.getDuration());
                Log.d("AppLogs", time);
            }
        });


        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    playpause.setImageResource(R.drawable.play);
                    mediaPlayer.pause();
                }
                else{
                    playpause.setImageResource(R.drawable.pause);
                    mediaPlayer.start();
                }

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position!=Song.SongFiles.size()-1){
                    position = position + 1;
                }
                else{
                    position = 0;
                }
                Uri uri = Uri.parse(Song.SongFiles.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
                playpause.setImageResource(R.drawable.pause);
                seekBar.setMax(mediaPlayer.getDuration());
                setSongDetails(position);

                String time = timeFormat(mediaPlayer.getDuration());
                Log.d("AppLogs", time);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String time = timeFormat(mediaPlayer.getCurrentPosition());
                Log.d("AppLogs", time);

                if(mediaPlayer.getDuration()-mediaPlayer.getCurrentPosition()>5000){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+5000);
                }
                else{
                    mediaPlayer.seekTo(mediaPlayer.getDuration());
                }

                time = timeFormat(mediaPlayer.getCurrentPosition());
                Log.d("AppLogs", time);
            }
        });



    }


    public static String timeFormat(int duration) {
        String time = "";
        int quotient=0;
        int remainder=0;

        quotient = duration/(3600*1000);
        remainder = duration%(3600*1000);
        if(quotient>0){
            String temp = (quotient < 10 ? "0" : "") + quotient + ":";
            time = time+temp;
        }

        quotient = remainder/(60*1000);
        remainder = remainder%(60*1000);

        String temp = (quotient < 10 ? "0" : "") + quotient + ":";
        time = time+temp;

        quotient = remainder/(1000);
        temp = (quotient < 10 ? "0" : "") + quotient;
        time = time+temp;
        return  time;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setSongDetails(int position){
        byte [] AlbumArt = Song.songPlayList[position].getAlbumArt();

        if(AlbumArt!=null) {

            Bitmap bitmap = BitmapFactory.decodeByteArray(AlbumArt, 0, AlbumArt.length);
            albumArt = findViewById(R.id.albumArt);
            albumArt.setImageBitmap(bitmap);

            Bitmap temp = bitmap.copy(bitmap.getConfig(), true);
            Bitmap outputBitmap = Bitmap.createBitmap(temp);

            RenderScript rs = RenderScript.create(MusicPlayer.this);
            ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            Allocation tmpIn = Allocation.createFromBitmap(rs, temp);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
            theIntrinsic.setRadius(17.5f);
            theIntrinsic.setInput(tmpIn);
            theIntrinsic.forEach(tmpOut);
            tmpOut.copyTo(outputBitmap);

            albumArtBlur = findViewById(R.id.albumArtBlur);
            albumArtBlur.setImageBitmap(outputBitmap);
            Log.d("AppLogs", "Song Album Art added");
        }else{
            albumArt = findViewById(R.id.albumArt);
            albumArt.setImageResource(R.drawable.music_icon);

            albumArtBlur = findViewById(R.id.albumArtBlur);
            albumArtBlur.setImageResource(R.drawable.music_icon);
        }

        songName = findViewById(R.id.songName);
        songDescription = findViewById(R.id.songDescription);
        songName.setSelected(true);

        Log.d("AppLogs", "Song Name & Description added");
        songName.setText(Song.songPlayList[position].getSongNames());
        songDescription.setText(Song.songPlayList[position].getSongArtist());

        timeSpent = findViewById(R.id.timeSpent);
        timeRemaining = findViewById(R.id.timeRemaining);

        timeSpent.setText("00:00");
        String time = timeFormat(mediaPlayer.getDuration());
        timeRemaining.setText(time);

    }

}