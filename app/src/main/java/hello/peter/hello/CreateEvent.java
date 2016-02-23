package hello.peter.hello;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import hello.peter.hello.Models.Event;
import hello.peter.hello.Models.Person;
import hello.peter.hello.Utils.Omni;

public class CreateEvent extends AppCompatActivity {

    protected Button submit;
    protected EditText Location, Summary, Name;
    protected Calendar calendar;
    protected TimePicker time;
    protected AlertDialog.Builder alertDialogBuilder;
    protected AlertDialog alertDialog;
    protected String location, name, summary, chosenTime;
    SharedPreferences prefs;
    int hour, minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        time = (TimePicker) findViewById(R.id.timePicker);
        submit = (Button)findViewById(R.id.button4);
        Location = (EditText)findViewById(R.id.location);
        Summary = (EditText)findViewById(R.id.summary);
        Name = (EditText)findViewById(R.id.eventName);
        calendar = Calendar.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEvent();
            }
        });
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                return;
            }
        });
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                createEvent();
            }
        });
    }

    private void checkEvent(){
        location = Location.getText().toString();
        name = Name.getText().toString();
        summary = Summary.getText().toString();

        if(name.length() == 0){
            Toast.makeText(getApplicationContext(), "Please enter an event name", Toast.LENGTH_LONG).show();
            return;
        }
        if(location.length() == 0){
            Toast.makeText(getApplicationContext(), "Please enter a location", Toast.LENGTH_LONG).show();
            return;
        }
        if(summary.length() == 0){
            Toast.makeText(getApplicationContext(), "Please enter a summary", Toast.LENGTH_LONG).show();
            return;
        }
        hour = time.getCurrentHour();
        minute = time.getCurrentMinute();

        if (hour == 0) {
            hour += 12;
            chosenTime = "A.M.";
        }
        else if (hour == 12) {
            chosenTime = "P.M.";
        } else if (hour > 12) {
            hour -= 12;
            chosenTime = "P.M.";
        } else {
            chosenTime = "A.M.";
        }
        if(minute < 10){
            alertDialogBuilder.setMessage(name + " will begin at " + hour + ":0" + minute + " " + chosenTime);
        }
        else {
            alertDialogBuilder.setMessage(name + " will begin at " + hour + ":" + minute + " " + chosenTime);
        }
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void createEvent(){
        prefs = getSharedPreferences(getString(R.string.shared_prefs), MODE_PRIVATE);
        String userName = prefs.getString("username", "");
        String passWord = prefs.getString("password", "");
        String phoneNo = prefs.getString("phonenumber", "");
        Person currentUser = new Person(userName,passWord,phoneNo, Omni.BitMapToString(getProfilePic()));
        Event newEvent = new Event(location,name,new ArrayList<Person>(),hour,minute,currentUser,summary);
        finish();
    }

    private Bitmap getProfilePic(){
        String yourFilePath = getApplicationContext().getFilesDir() + "/" + "map.png";
        File yourFile = new File(yourFilePath);
        return BitmapFactory.decodeFile(yourFilePath);
    }

}
