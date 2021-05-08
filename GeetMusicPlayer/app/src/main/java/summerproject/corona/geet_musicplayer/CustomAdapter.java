package summerproject.corona.geet_musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

//    @Nullable
//    @Override
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
        // Before returning the view, add Click listener
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You Clicked on: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }


}
