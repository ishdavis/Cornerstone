package hello.peter.hello;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ishdavis on 2/28/2016.
 */
public class notificationViewHolder extends RecyclerView.ViewHolder {

    public static TextView userName, position;
    public static ImageView img;
    public static ImageButton Accept, Reject;

    public notificationViewHolder(View itemView) {
        super(itemView);
        userName = (TextView)itemView.findViewById(R.id.notificationName);
        img = (ImageView)itemView.findViewById(R.id.notificationPic);
        Accept = (ImageButton)itemView.findViewById(R.id.Accept);
        Reject = (ImageButton)itemView.findViewById(R.id.Reject);
        position = (TextView)itemView.findViewById(R.id.position);
    }
}
