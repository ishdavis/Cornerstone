package hello.peter.hello.Utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.database.Cursor;
import android.app.LoaderManager;
import android.content.Context;
import android.util.Base64;
import android.app.PendingIntent;
import android.telephony.gsm.SmsManager;
import android.content.Intent;
import java.io.ByteArrayOutputStream;
import android.content.SharedPreferences;
import com.firebase.client.Firebase;
import android.util.Base64;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ishdavis on 1/27/2016.
 */
public class Omni extends Activity{

    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b = baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static void sendText(String number, String text){
        //PendingIntent pi = PendingIntent.getActivity(context, 0,
                //new Intent(context, SignUp.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage( number, null, text, null, null);
    }

    public static void sendInvite(String number, String name){
        String text = "Hi " + name + " Click the following link to join me at Forge!!\nforge.x10host.com";
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage( number, null, text, null, null);

    }

    public static void addFriend(String userName, String friendUsername){
        Firebase RefTest = new Firebase("https://dazzling-fire-8069.firebaseio.com/friends/" + userName);
        Map<String,Object> addUser = new HashMap<String,Object>();
        addUser.put(friendUsername, 1);
        RefTest.updateChildren(addUser);
    }


}
