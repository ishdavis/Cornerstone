package hello.peter.hello;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Intent;
import android.app.Activity;
import android.util.SparseArray;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.content.Context;
import java.sql.*;
import android.os.AsyncTask;
import android.util.Pair;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;
import java.lang.System.*;
import java.io.*;
import java.sql.*;
import android.widget.Button;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.CursorLoader;

import com.firebase.client.FirebaseError;
import com.google.gson.Gson;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.content.ContentResolver;
import android.view.WindowManager;
import android.view.Window;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import android.util.Base64;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import android.content.SharedPreferences;


public class Interests extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    SparseArray<Group> groups = new SparseArray<Group>();
    ArrayList<String> names, number;
    Button submitButton;
    public Handler updateBarHandler;
    public AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    String userName;
    public MyExpandableListAdapter adapter;
    final ArrayList<Integer> member = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_interests);
        Intent intent = getIntent();
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.shared_prefs), MODE_PRIVATE);
        userName = prefs.getString("username", null);
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please choose all activities that interest you");
        alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        getWindow().getDecorView().setBackgroundColor(Color.rgb(237, 24, 69));
        createData();
        updateBarHandler = new Handler();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);
        submitButton = (Button)findViewById(R.id.interests);
        //20 is hardcoded
        adapter = new MyExpandableListAdapter(this,
                groups, groups.size(), 20);
        listView.setAdapter(adapter);
        getLoaderManager().initLoader(1,
                null,
                this);
    }

    /**
     * Save the cursor that contains the user's contacts
     * @param cursor
     */
    private void saveCursor(Cursor cursor){
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cursor);
        prefsEditor.putString("MyCursor", json);
        prefsEditor.commit();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //The framework will take care of closing the
        // old cursor once we return.
        final Cursor temp = cursor;
        final ProgressDialog dialog = ProgressDialog.show(Interests.this, "", "Loading Interests....", true);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    contactsFromCursor(temp);
                    dialog.dismiss();
                }
                catch (Exception e) {
                }
            }
        }).start();
        //saveCursor(cursor); Might need this later
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase pushInterests = new Firebase("https://dazzling-fire-8069.firebaseio.com/users")
                        .child(userName).child("interests");
                pushInterests.setValue(adapter.getInterests());
                Intent i = new Intent(Interests.this, Addfriends.class);
                i.putStringArrayListExtra("names", names);
                i.putStringArrayListExtra("number", number);
                i.putIntegerArrayListExtra("member", member);
                Interests.this.startActivity(i);
            }
        });
        submitButton.setEnabled(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.

        if (id == 1) {
            return contactsLoader();
        }
        return null;
    }

    private  Loader<Cursor> contactsLoader() {
        Uri contactsUri = ContactsContract.Contacts.CONTENT_URI; // The content URI of the phone contacts

        String[] projection = {                                  // The columns to return for each row
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts._ID
        } ;

        String selection = null;                                 //Selection criteria
        String[] selectionArgs = {};                             //Selection criteria
        String sortOrder = null;                                 //The sort order for the returned rows

        return new CursorLoader(
                getApplicationContext(),
                contactsUri,
                projection,
                selection,
                selectionArgs,
                sortOrder);
    }

    private void contactsFromCursor(Cursor cursor) {
        names = new ArrayList<String>();
        number = new ArrayList<String>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String rawNum = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        final String phoneNo = rawNum.replaceAll("[^0-9]", "");
                        names.add(name);
                        number.add(phoneNo);
                        Firebase userRef = new Firebase("https://dazzling-fire-8069.firebaseio.com/numbers/" + phoneNo);
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    member.add(1);
                                } else {
                                    member.add(0);
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                        break;
                    }
                    pCur.close();
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    /*
     * Clears out the adapter's reference to the Cursor.
     * This prevents memory leaks.
     */
    }

    /**
     * Do nothing on back press
     */
    @Override
    public void onBackPressed(){
        return;
    }

    /**
     * Add interests here
     */
    private void createData(){
        Group sports = new Group("Sports");
        sports.children.add("Basketball");
        sports.children.add("Football");
        sports.children.add("Soccer");
        sports.children.add("Tennis");
        sports.children.add("Baseball");
        sports.children.add("Weight lifting");

        Group food = new Group("Food");
        food.children.add("American");
        food.children.add("Thai");
        food.children.add("Indian");
        food.children.add("Spanish");
        food.children.add("African");
        food.children.add("Chinese-Japanese");

        Group music = new Group("Music");
        music.children.add("Rap-Hip-Hop");
        music.children.add("Country");
        music.children.add("Rock");
        music.children.add("Pop");
        music.children.add("Dance-EDM");
        music.children.add("Religious");
        music.children.add("International");

        Group night = new Group("Nightlife");
        night.children.add("Dance Clubs");
        night.children.add("Bars");
        night.children.add("Movies");
        night.children.add("Concerts");
        night.children.add("Community Events");

        Group life = new Group("Lifestyle");
        life.children.add("Christianity");
        life.children.add("Islam");
        life.children.add("Judaism");
        life.children.add("LGBTQ");

        Group academics = new Group("Academics");
        academics.children.add("Science");
        academics.children.add("Technology");
        academics.children.add("Business");
        academics.children.add("Arts");
        academics.children.add("Engineering");
        academics.children.add("Medical");
        academics.children.add("Social");

        groups.append(1, food);
        groups.append(0, sports);
        groups.append(2, music);
        groups.append(3, night);
        groups.append(4, life);
        groups.append(5, academics);
    }

}
