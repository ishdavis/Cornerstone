package hello.peter.hello;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;


import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.text.DateFormat;
import java.util.Calendar;

import hello.peter.hello.Models.Event;
import hello.peter.hello.Utils.Omni;
import hello.peter.hello.util.DividerItemDecoration;


public class tab1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    protected RecyclerView skeleton;
    protected FirebaseRecyclerAdapter<Event,EventViewHolder> adapter;
    protected Context context;
    public ProgressDialog barProgressDialog;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static tab1 newInstance(String param1, String param2) {
        tab1 fragment = new tab1();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        Firebase.setAndroidContext(context);
        skeleton = (RecyclerView)view.findViewById(R.id.allActivities);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST);
        skeleton.addItemDecoration(itemDecoration);
        //skeleton.setHasFixedSize(true);
        LinearLayoutManager manage = new LinearLayoutManager(context);
        manage.setReverseLayout(true);
        manage.setStackFromEnd(true);
        skeleton.setLayoutManager(manage);
        Firebase ref = new Firebase(Omni.RootRef).child("event");
        adapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(Event.class, R.layout.event_row, EventViewHolder.class, ref) {
            @Override
            protected void populateViewHolder(EventViewHolder eventViewHolder, Event event, int i) {
                EventViewHolder.eventTitle.setText(event.getName());
                EventViewHolder.eventPic.setImageBitmap(Omni.StringToBitMap(event.getCreator().getPicURL()));
                EventViewHolder.userName.setText(event.getCreator().getUserName());
                EventViewHolder.Location.setText(event.getLocation());
                EventViewHolder.Summary.setText(event.getSummary());
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




    }
