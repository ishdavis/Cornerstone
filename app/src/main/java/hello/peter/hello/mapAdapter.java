package hello.peter.hello;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import android.telephony.gsm.SmsManager;

import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import hello.peter.hello.Utils.Omni;

/**
     * Created by Ishdavis on 1/30/2016.
     */
    public class mapAdapter extends
            RecyclerView.Adapter<mapAdapter.ViewHolder>{

        public static class ViewHolder extends RecyclerView.ViewHolder{
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView name;
            public Button invite;
            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.addFriends);
                invite = (Button) itemView.findViewById(R.id.invite_button);

            }
            }

        final protected ArrayList<String> names, numbers;
        final protected ArrayList<Integer> member;
        protected int [] memo;
        protected Drawable curr;
        protected Context context;
        final protected String userName;
        // Pass in the contact array into the constructor
        public mapAdapter(ArrayList<String> name, ArrayList<String> number, Context in, ArrayList<Integer> members, String userName) {
            names = name;
            numbers = number;
            memo = new int[names.size()];
            context = in;
            member = members;
            this.userName = userName;
        }

        @Override
        public mapAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.friendadder, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(mapAdapter.ViewHolder viewHolder, int position) {
            TextView view = viewHolder.name;
            view.setText(names.get(position));
            Button but = viewHolder.invite;
            if(curr == null) {
                curr = but.getBackground();
            }

            if(memo[position] == 1){//If it has been clicked
                but.setText("");
                but.setEnabled(false);
                but.setBackgroundResource(R.mipmap.ic_check_mark_md);
            }
            else {//if it hasn't been clicked
                but.setBackgroundDrawable(curr);
                but.setEnabled(true);
                if(member.get(position) == 0){
                    but.setText("Invite");
                }
                else{
                    but.setText("Add");
                }
            }
            final Button realButton = but;
            final int pos = position;
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    realButton.setText("");
                    realButton.setBackgroundResource(R.mipmap.ic_check_mark_md);
                    memo[pos] = 1;
                    realButton.setEnabled(false);
                    if(member.get(pos) == 0){//Invite, send text
                        Omni.sendInvite(numbers.get(pos), names.get(pos));
                        Toast.makeText(context, "Friend Invited", Toast.LENGTH_SHORT).show();
                    }
                    else{//Add through firebase
                        //Get username
                        Firebase userRef = new Firebase("https://dazzling-fire-8069.firebaseio.com/numbers").child(numbers.get(pos));
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                             String friendName = (String)dataSnapshot.getValue();
                             Omni.addFriend(userName,friendName);//Possibly change this to friend requests
                               Toast.makeText(context, "Friend Request Sent", Toast.LENGTH_SHORT).show();
                           }

                           @Override
                           public void onCancelled(FirebaseError firebaseError) {

                           }
                       });
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return names.size();
        }
    }

