package hello.peter.hello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import hello.peter.hello.Models.Event;
import hello.peter.hello.Models.FriendRequest;
import hello.peter.hello.Models.Person;
import hello.peter.hello.Utils.Omni;
import hello.peter.hello.util.DividerItemDecoration;

public class Notifications extends AppCompatActivity {

    public RecyclerView skeleton;
    protected FirebaseRecyclerAdapter<FriendRequest, notificationViewHolder> adapter;
    protected String userName;
    protected Map<Integer,String> friendKeeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        friendKeeper = new HashMap<Integer,String>();
        skeleton = (RecyclerView)findViewById(R.id.Notifications);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        skeleton.addItemDecoration(itemDecoration);
        skeleton.setHasFixedSize(true);
        LinearLayoutManager manage = new LinearLayoutManager(this);
        skeleton.setLayoutManager(manage);
        skeleton.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = skeleton.findChildViewUnder(e.getX(), e.getY());
                TextView key = (TextView)child.findViewById(R.id.position);
                String from = key.getText().toString();
                final Firebase adder = new Firebase(Omni.RootRef).child("users").child(from);
                adder.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final Person p = dataSnapshot.getValue(Person.class);
                        final Map<String,Object> interests = new HashMap<String,Object>();
                        final Map<String,Object> interest = new HashMap<String,Object>();
                        Firebase inter = adder.child("interests");
                        inter.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    interests.put(child.getKey(), child.getValue());
                                }
                                Firebase add = new Firebase(Omni.RootRef).child("friends").child(userName).child(p.getUserName());
                                add.setValue(p);
                                interest.put("interests", interests);
                                add.updateChildren(interest);
                                Toast.makeText(getApplicationContext(), p.getUserName() + " has been added", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                return true;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


            SharedPreferences prefs = this.getSharedPreferences(getString(R.string.shared_prefs), this.MODE_PRIVATE);
            userName=prefs.getString("username","");

            Firebase ref = new Firebase(Omni.RootRef).child("users").child(userName).child("notifications").child("FriendRequests");
            adapter=new FirebaseRecyclerAdapter<FriendRequest, notificationViewHolder>(FriendRequest.class,R.layout.notificationrow,notificationViewHolder.class,ref)

            {
                @Override
                protected void populateViewHolder (
                final notificationViewHolder nView, FriendRequest request,int i){
                friendKeeper.put(i, request.getFrom());
                nView.userName.setText("Add Friend: " + request.getFrom() + "?");
                    Bitmap map = Omni.StringToBitMap(request.getImg());
                    map = Bitmap.createScaledBitmap(map, 60, 60, true);
                nView.img.setImageBitmap(map);
                nView.Accept.setImageResource(R.mipmap.ic_check_mark_md);
                nView.position.setText(request.getFrom());

                //ImageButton accept = (ImageButton)find
            }
            }

            ;
            skeleton.setAdapter(adapter);
        }


    }
