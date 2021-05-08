package summerproject.corona.geet_musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomAdapterVideo extends ArrayAdapter<Video> {

    private final Context context;

    public CustomAdapterVideo(@NonNull Context context, int resource, @NonNull Video[] videoPlayList) {
        super(context, resource, videoPlayList);

        this.context = context;
    }

    public Video getItem(int position) {
        return Video.videoPlayList[position];
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_listview_layout_video, parent, false);
        TextView videoName = convertView.findViewById(R.id.videoName);

        videoName.setText(getItem(position).getVideoNames());
        TextView videoDescription = convertView.findViewById(R.id.videoDescription);
        videoDescription.setText(getItem(position).getVideoArtist());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AppLogs", "Playing video : "+getItem(position).getVideoNames());
                final Intent intent_VideoPlayer = new Intent(context, VideoPlayer.class);
                intent_VideoPlayer.putExtra("position", position);
                context.startActivity(intent_VideoPlayer);
            }

        });
        return convertView;
    }
}