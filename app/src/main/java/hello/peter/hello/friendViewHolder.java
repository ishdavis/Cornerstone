package hello.peter.hello;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Ishdavis on 2/28/2016.
 */
public class friendViewHolder extends RecyclerView.ViewHolder {

    public static TextView friendName;
    public static ProgressBar commons;
    public static ImageButton deleteFriend;
    public static ImageView profilePic;

    public friendViewHolder(View itemView){
        super(itemView);
        friendName = (TextView)itemView.findViewById(R.id.friendName);
        commons = (ProgressBar)itemView.findViewById(R.id.commonInterests);
        deleteFriend = (ImageButton)itemView.findViewById(R.id.deleteFriend);
        profilePic = (ImageView)itemView.findViewById(R.id.profilePic);
    }
}
