package hello.peter.hello;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;

/**
 * Created by Ishdavis on 2/24/2016.
 */
public class EventViewHolder extends RecyclerView.ViewHolder{
    public static TextView eventTitle, userName, Location, Summary;
    public static ImageView eventPic;

    public EventViewHolder(View itemView){
        super(itemView);
        eventTitle = (TextView)itemView.findViewById(R.id.eventText);
        eventPic = (ImageView)itemView.findViewById(R.id.eventPic);
        userName = (TextView)itemView.findViewById(R.id.userName);
        Location = (TextView)itemView.findViewById(R.id.Location);
        Summary = (TextView)itemView.findViewById(R.id.Summary);
    }
}
