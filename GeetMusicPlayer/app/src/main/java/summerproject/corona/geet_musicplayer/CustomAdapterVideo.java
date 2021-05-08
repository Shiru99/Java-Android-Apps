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



public class CustomAdapterVideo extends ArrayAdapter<Video> {

    private Video[] videoPlayList;
    private Context context;


    public CustomAdapterVideo(@NonNull Context context, int resource, @NonNull Video[] videoPlayList) {
        super(context, resource, videoPlayList);

        this.context = context;
        this.videoPlayList = videoPlayList;
    }

    //    @Nullable
//    @Override
    public Video getItem(int position) {
        return videoPlayList[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_listview_layout_video, parent, false);
        TextView videoName = convertView.findViewById(R.id.videoName);

        videoName.setText(getItem(position).getVideoNames());
        TextView videoDescription = convertView.findViewById(R.id.videoDescription);
        videoDescription.setText(getItem(position).getVideoArtist());
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
