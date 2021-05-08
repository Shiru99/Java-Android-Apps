package summerproject.corona.geet_musicplayer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomAdapter extends ArrayAdapter<Song> {

    private Song[] songPlayList;
    private Context context;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull Song[] songPlayList) {
        super(context, resource, songPlayList);

        this.context = context;
        this.songPlayList = songPlayList;
    }

    public Song getItem(int position) {
        return songPlayList[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_listview_layout, parent, false);
        TextView songName = convertView.findViewById(R.id.videoName);

        songName.setText(getItem(position).getSongNames());
        TextView songDescription = convertView.findViewById(R.id.videoDescription);
        songDescription.setText(getItem(position).getSongArtist());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AppLogs", "Playing Song : "+getItem(position).getSongNames());
                final Intent intent_MusicPlayer = new Intent(context, MusicPlayer.class);
                intent_MusicPlayer.putExtra("position", position);
                context.startActivity(intent_MusicPlayer);
            }
        });

        return convertView;
    }
}