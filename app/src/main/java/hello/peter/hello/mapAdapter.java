package hello.peter.hello;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import android.widget.Button;
   /**
     * Created by Ishdavis on 1/30/2016.
     */
    public class mapAdapter extends
            RecyclerView.Adapter<mapAdapter.ViewHolder>{

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
                if(invite.isEnabled() == false){
                    int k = 5;
                }
                name.setOnClickListener(this);
                invite.setOnClickListener(this);
                itemView.setOnClickListener(this);
            }

            // onClick Listener for view
            @Override
            public void onClick(View v) {
                if(v.getId() == invite.getId()){
                    //invite.setVisibility(View.INVISIBLE);
                    invite.setEnabled(false);
                    //invited.setVisibility(View.VISIBLE);
                }
                else if(v.getId() == name.getId()){}
            }
            }

        protected ArrayList<String> names, numbers;
        // Pass in the contact array into the constructor
        public mapAdapter(ArrayList<String> name, ArrayList<String> number) {
            names = name;
            numbers = number;
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
            if(position % 2 == 0){but.setText("invite");}
            else{but.setText("add");}
            //final Button realButton = but;
            //final int pos = position;
//            but.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    realButton.setText(numbers.get(pos));
//                    realButton.setVisibility(View.INVISIBLE);
//                }
//            });
            // + numbers.get(position));
            /*pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //int position = getLayoutPosition();
                    savedState cur = MainActivity.customCanvas.getPaths().get(po);
                    MainActivity.customCanvas.drawSaved(cur.getMyPath());

                }
            });*/
            //add touch events
        }



        @Override
        public int getItemCount() {
            return names.size();
        }
    }

