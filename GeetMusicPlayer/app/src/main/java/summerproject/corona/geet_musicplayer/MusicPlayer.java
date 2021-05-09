package summerproject.corona.geet_musicplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.TextView;

public class MusicPlayer extends AppCompatActivity {

    private ImageView albumArt,albumArtBlur;
    private TextView songName,songDescription;
    private MediaPlayer mediaPlayer;;
    int position;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Log.d("AppLogs", "Entered in Music Player");
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        Log.d("AppLogs", ""+position);

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
        }

        songName = findViewById(R.id.songName);
        songDescription = findViewById(R.id.songDescription);
        songName.setSelected(true);

        songName.setText(Song.songPlayList[position].getSongNames());
        songDescription.setText(Song.songPlayList[position].getSongArtist());



    }



}