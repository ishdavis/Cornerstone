package hello.peter.hello;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.ContentResolver;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.database.Cursor;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.widget.SearchView;

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import hello.peter.hello.Utils.Omni;
import android.net.Uri;
import android.content.CursorLoader;
import java.net.URI;
import android.provider.ContactsContract.CommonDataKinds.*;
import java.util.Map;
import java.util.HashMap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import hello.peter.hello.util.DividerItemDecoration;
import com.firebase.client.Firebase;
import android.util.Base64;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class Addfriends extends AppCompatActivity{

    ArrayList<String> names, number;
    ArrayList<Integer> member;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SearchView search = (SearchView)findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Firebase userRef = new Firebase("https://dazzling-fire-8069.firebaseio.com/users/").child(query);
                final String friendName = query;
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            //Add friend here
                            Omni.addFriend(userName,friendName);
                            Toast.makeText(getApplicationContext(), "Friend Request Sent", Toast.LENGTH_SHORT).show();
                        }
                        else{Toast.makeText(getApplicationContext(), "UserName not found", Toast.LENGTH_SHORT).show();}
                        }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        names = getIntent().getExtras().getStringArrayList("names");//contact names
        number = getIntent().getExtras().getStringArrayList("number");//contact numbers
        member = getIntent().getExtras().getIntegerArrayList("member");//Whether the contact is a member
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.friendView);
        rvContacts.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
               DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvContacts.addItemDecoration(itemDecoration);
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.shared_prefs), Context.MODE_PRIVATE);
        userName = prefs.getString("username", null);
        mapAdapter adapter = new mapAdapter(names,number,getApplicationContext(), member, userName);

        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }

}
