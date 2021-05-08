package summerproject.corona.geet_musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class VideoPlayer extends AppCompatActivity {

    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        Log.d("AppLogs", "Entered in Video Player");
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        Log.d("AppLogs", ""+position);
    }
}