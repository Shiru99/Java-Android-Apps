package summerproject.corona.geet_musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

public class VideoPlayer extends AppCompatActivity {

    private SurfaceView surfaceView;
    private TextView videoName,videoDescription;
    private MediaPlayer mediaPlayer;
    private ImageButton backward,previous, playpause, next, forward;
    private TextView timeSpent,timeRemaining;
    private SeekBar seekBar;
    private Thread updateSeek;

    int position;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        updateSeek.interrupt();
        mediaPlayer.stop();
        mediaPlayer.release();
        Log.d("AppLogs","onBackPressed");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
//        if (getSupportActionBar() != null)
//            getSupportActionBar().hide();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        Log.d("AppLogs", "Entered in Video Player");
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        backward = findViewById(R.id.backward);
        previous = findViewById(R.id.previous);
        playpause = findViewById(R.id.playpause);
        next = findViewById(R.id.next);
        forward = findViewById(R.id.forward);
        seekBar = findViewById(R.id.seekBar);
        surfaceView = findViewById(R.id.surfaceView);
        videoName = findViewById(R.id.videoName);
        videoDescription = findViewById(R.id.videoDescription);
        timeSpent = findViewById(R.id.timeSpent);
        timeRemaining = findViewById(R.id.timeRemaining);

//        backward.setRotation(90);
//        previous.setRotation(90);
//        playpause.setRotation(90);
//        next.setRotation(90);
//        forward.setRotation(90);
//        surfaceView.setRotation(90);

        Uri uri = Uri.parse(Video.videoFiles.get(position).toString());
        mediaPlayer = MediaPlayer.create(this, uri);


//                surfaceView.setOrientationHint(90);
//        mediaPlayer = MediaPlayer.create(this, R.raw.vid);
        surfaceView.setKeepScreenOn(true);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();


        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                mediaPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });

        mediaPlayer.start();
        playpause.setImageResource(R.drawable.pause);
        Log.d("AppLogs", "Video Started Playing");


        setVideoDetails(position);

        // SeekBar
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
                        sleep(200);
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

                int currentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(currentPosition);

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
                    position = Video.videoFiles.size() - 1;
                }
                Uri uri = Uri.parse(Video.videoFiles.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.setDisplay(surfaceHolder);
                mediaPlayer.start();
                playpause.setImageResource(R.drawable.pause);
                seekBar.setMax(mediaPlayer.getDuration());
                setVideoDetails(position);

                int currentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(currentPosition);

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
                if(position!=Video.videoFiles.size()-1){
                    position = position + 1;
                }
                else{
                    position = 0;
                }
                Uri uri = Uri.parse(Video.videoFiles.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.setDisplay(surfaceHolder);
                mediaPlayer.start();
                playpause.setImageResource(R.drawable.pause);
                seekBar.setMax(mediaPlayer.getDuration());
                setVideoDetails(position);
                int currentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(currentPosition);

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
                int currentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(currentPosition);

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
    public void setVideoDetails(int position){
        Log.d("AppLogs", "Video Name & Description added");
        videoName.setSelected(true);
        videoName.setText(Video.videoPlayList[position].getVideoNames());
        videoDescription.setText(Video.videoPlayList[position].getVideoArtist());

        timeSpent = findViewById(R.id.timeSpent);
        timeRemaining = findViewById(R.id.timeRemaining);

        timeSpent.setText("00:00");
        String time = timeFormat(mediaPlayer.getDuration());
        timeRemaining.setText(time);

    }

}