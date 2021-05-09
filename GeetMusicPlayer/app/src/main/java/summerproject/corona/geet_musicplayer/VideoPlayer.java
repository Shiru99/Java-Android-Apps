package summerproject.corona.geet_musicplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class VideoPlayer extends AppCompatActivity {

    private SurfaceView surfaceView;
    private TextView videoName;
    private MediaPlayer mediaPlayer;
    private ImageButton backward,previous, playpause, next, forward;
    private TextView timeSpent,timeRemaining;
    private SeekBar seekBar;
    private Thread updateSeek;

    int position;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        Log.d("AppLogs", "Entered in Video Player");
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        Log.d("AppLogs", ""+position);

        backward = findViewById(R.id.backward);
        previous = findViewById(R.id.previous);
        playpause = findViewById(R.id.playpause);
        next = findViewById(R.id.next);
        forward = findViewById(R.id.forward);
        seekBar = findViewById(R.id.seekBar);

//        backward.setRotation(90);
//        previous.setRotation(90);
//        playpause.setRotation(90);
//        next.setRotation(90);
//        forward.setRotation(90);
        setVideoDetails(position);


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


        videoName = findViewById(R.id.videoName);
        videoName.setSelected(true);



        Log.d("AppLogs", "Video Name & Description added");
        videoName.setText(Video.videoPlayList[position].getVideoNames());

        timeSpent = findViewById(R.id.timeSpent);
        timeRemaining = findViewById(R.id.timeRemaining);

//        timeSpent.setText("00:00");
//        String time = timeFormat(mediaPlayer.getDuration());
//        timeRemaining.setText(time);

    }

}