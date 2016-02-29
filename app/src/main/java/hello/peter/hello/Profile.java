package hello.peter.hello;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import hello.peter.hello.Models.Event;
import hello.peter.hello.Models.Person;
import hello.peter.hello.Utils.Omni;
import hello.peter.hello.Utils.RoundedAvatarDrawable;
import hello.peter.hello.util.DividerItemDecoration;

public class Profile extends AppCompatActivity {

    public ImageView pic;
    public RecyclerView skeleton;
    protected FirebaseRecyclerAdapter<Person,friendViewHolder> adapter;
    protected Map<String,Object> interests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //pic.setImageDrawable(new RoundedAvatarDrawable())
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.shared_prefs), MODE_PRIVATE);
        String userName = prefs.getString("username", "");
        Firebase self = new Firebase(Omni.RootRef).child("users").child(userName).child("interests");
        interests = new HashMap<String,Object>();
        self.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    interests.put(child.getKey(),child.getValue());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //String passWord = prefs.getString("password", "");
        //String phoneNo = prefs.getString("phonenumber", "");
        //Person currentUser = new Person(userName, passWord, phoneNo, Omni.BitMapToString(getProfilePic()));

        skeleton = (RecyclerView)findViewById(R.id.allFriends);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        skeleton.addItemDecoration(itemDecoration);
        //skeleton.setHasFixedSize(true);
        LinearLayoutManager manage = new LinearLayoutManager(this);
        skeleton.setLayoutManager(manage);


        Firebase ref = new Firebase(Omni.RootRef).child("friends").child(userName);
        adapter = new FirebaseRecyclerAdapter<Person, friendViewHolder>(Person.class, R.layout.friendrow, friendViewHolder.class, ref) {
            @Override
            protected void populateViewHolder(final friendViewHolder ViewHolder, Person person, int i) {
                Random generator = new Random();
                ViewHolder.friendName.setText(person.getUserName());
                Bitmap map = Omni.StringToBitMap(person.getPicURL());
                map = Bitmap.createScaledBitmap(map, 60, 60, true);
                ViewHolder.profilePic.setImageBitmap(map);
                ViewHolder.commons.setProgress(generator.nextInt(100));
                ViewHolder.commons.getProgressDrawable().setColorFilter(Color.parseColor("#ed1845"), PorterDuff.Mode.SRC_IN);

            }
        };
        skeleton.setAdapter(adapter);
    }

    private Bitmap getProfilePic(){
        String yourFilePath = this.getFilesDir() + "/" + "bitmap.png";
        File yourFile = new File(yourFilePath);
        return BitmapFactory.decodeFile(yourFilePath);
    }

}
