package hello.peter.hello;

import android.os.Bundle;
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


public class Addfriends extends AppCompatActivity{

    ArrayList<String> names, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        names = getIntent().getExtras().getStringArrayList("names");
        number = getIntent().getExtras().getStringArrayList("number");
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.friendView);
        rvContacts.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
               DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvContacts.addItemDecoration(itemDecoration);
        mapAdapter adapter = new mapAdapter(names,number);

        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        // This is called when a new Loader needs to be created.
//
//        if (id == 1) {
//            return contactsLoader();
//        }
//        return null;
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//        //The framework will take care of closing the
//        // old cursor once we return.
//        //contactsFromCursor(cursor);
////        names = new ArrayList<String>();
////        number = new ArrayList<String>();
////        names.add("Hi");
////        number.add("Hellooo");
////        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.friendView);
////        mapAdapter adapter = new mapAdapter(names,number);
////        rvContacts.setAdapter(adapter);
////        rvContacts.setLayoutManager(new LinearLayoutManager(this));
//        int k = 10;
//    }
//
//    private  Loader<Cursor> contactsLoader() {
//        Uri contactsUri = ContactsContract.Contacts.CONTENT_URI; // The content URI of the phone contacts
//
//        String[] projection = {                                  // The columns to return for each row
//                ContactsContract.Contacts.DISPLAY_NAME,
//                ContactsContract.Contacts._ID
//        } ;
//
//        String selection = null;                                 //Selection criteria
//        String[] selectionArgs = {};                             //Selection criteria
//        String sortOrder = null;                                 //The sort order for the returned rows
//
//        return new CursorLoader(
//                getApplicationContext(),
//                contactsUri,
//                projection,
//                selection,
//                selectionArgs,
//                sortOrder);
//    }
//
//    private void contactsFromCursor(Cursor cursor) {
//        names = new ArrayList<String>();
//        number = new ArrayList<String>();
//        ContentResolver cr = getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
//                null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
//        if (cur.getCount() > 0) {
//            while (cur.moveToNext()) {
//                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                if (Integer.parseInt(cur.getString(
//                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                    Cursor pCur = cr.query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                            new String[]{id}, null);
//                    while (pCur.moveToNext()) {
//                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        names.add(name);
//                        number.add(phoneNo);
//                    }
//                    pCur.close();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//
//    /*
//     * Clears out the adapter's reference to the Cursor.
//     * This prevents memory leaks.
//     */
//    }

}
