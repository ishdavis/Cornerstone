package hello.peter.hello;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;

import org.w3c.dom.Text;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import hello.peter.hello.Models.Event;
import hello.peter.hello.Models.Person;
import hello.peter.hello.Utils.Omni;
import hello.peter.hello.Utils.RoundedAvatarDrawable;
import hello.peter.hello.util.DividerItemDecoration;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * interface
 * to handle interaction events.
 * Use the {@link Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    protected RecyclerView skeleton;
    protected FirebaseRecyclerAdapter<Event,EventViewHolder> adapter;
    protected Context context;
    public ProgressDialog barProgressDialog;
    public int Called = 0;
    protected String userName;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        skeleton = (RecyclerView)view.findViewById(R.id.allActivities);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST);
        skeleton.addItemDecoration(itemDecoration);
        //skeleton.setHasFixedSize(true);
        LinearLayoutManager manage = new LinearLayoutManager(context);
        manage.setReverseLayout(true);
        manage.setStackFromEnd(true);
        skeleton.setLayoutManager(manage);

        final GestureDetector mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


        skeleton.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = skeleton.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mGestureDetector.onTouchEvent(e)) {
                    TextView key = (TextView) child.findViewById(R.id.eventHash);
                    String eventKey = key.getText().toString();
                    final Firebase names = new Firebase(Omni.RootRef).child("event").child(eventKey).child("members");
                    final ArrayList<String> name = new ArrayList<String>();
                    names.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot post : dataSnapshot.getChildren()) {
                                name.add(post.getKey());
                            }
                            if (Called == 0) {
                                Called = 1;
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                View convertView = (View) inflater.inflate(R.layout.eventmembers, null);
                                alertDialog.setView(convertView);
                                alertDialog.setTitle("Members");
                                alertDialog.setCancelable(false);
                                ListView lv = (ListView) convertView.findViewById(R.id.eventMembers);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, name);
                                lv.setAdapter(adapter);

                                alertDialog.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Called = 0;
                                    }
                                });
                                alertDialog.setPositiveButton("Join", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferences prefs = context.getSharedPreferences(getString(R.string.shared_prefs), context.MODE_PRIVATE);
                                        userName = prefs.getString("username", "");
                                        String passWord = prefs.getString("password", "");
                                        String phoneNo = prefs.getString("phonenumber", "");
                                        Person currentUser = new Person(userName, passWord, phoneNo, Omni.BitMapToString(getProfilePic()));
                                        Map<String, Object> addMe = new HashMap<String, Object>();
                                        addMe.put(currentUser.getUserName(), currentUser);
                                        names.child(userName).setValue(currentUser);
                                        Called = 0;
                                    }
                                });
                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        if(userName == null) {
            SharedPreferences prefs = context.getSharedPreferences(getString(R.string.shared_prefs), context.MODE_PRIVATE);
            userName = prefs.getString("username", "");
        }
        //Firebase ref = new Firebase(Omni.RootRef).child("friends").child(userName); later
        Firebase ref = new Firebase(Omni.RootRef).child("event");
        adapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(Event.class, R.layout.event_row, EventViewHolder.class, ref) {
            @Override
            protected void populateViewHolder(final EventViewHolder eventViewHolder, Event event, int i) {
                EventViewHolder.eventTitle.setText(event.getCreator().getUserName());
                Bitmap map = Omni.StringToBitMap(event.getCreator().getPicURL());
                map = Bitmap.createScaledBitmap(map, 60, 60, true);
                EventViewHolder.eventPic.setImageBitmap(map);
                EventViewHolder.userName.setText(event.getName() + " @ " + transformTime(event.getHour(),event.getMinute()));
                EventViewHolder.Location.setText(event.getLocation());
                EventViewHolder.Summary.setText(event.getSummary());
                EventViewHolder.Hash.setText(event.getKey());
            }
        };
        skeleton.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    private Bitmap getProfilePic(){
        String yourFilePath = context.getFilesDir() + "/" + "bitmap.png";
        File yourFile = new File(yourFilePath);
        return BitmapFactory.decodeFile(yourFilePath);
    }

    private String transformTime(int hour, int minute){
        StringBuilder time = new StringBuilder(hour);
        String zone;
        if (hour == 0) {
            hour += 12;
            zone = "A.M.";
        }
        else if (hour == 12) {
            zone = "P.M.";
        } else if (hour > 12) {
            hour -= 12;
            zone = "P.M.";
        } else {
            zone = "A.M.";
        }
        time.append(hour);
        time.append(":");
        if(minute < 10){time.append("0");}
        time.append(minute);
        time.append(" ");
        time.append(zone);
        return time.toString();
    }


}
