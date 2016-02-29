package hello.peter.hello.Models;

import android.graphics.Bitmap;

/**
 * Created by Ishdavis on 2/28/2016.
 */
public class FriendRequest {
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String from;
    public String img;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String key;

    public FriendRequest(){}

    public FriendRequest(String from, String pic, String key){
        this.from = from;
        img = pic;
        this.key = key;
    }
}
