package hello.peter.hello;

import android.database.Cursor;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import android.graphics.Matrix;
import java.io.ByteArrayOutputStream;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import java.sql.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import hello.peter.hello.Models.Person;
import android.content.SharedPreferences;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.amazonaws.mobileconnectors.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.*;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.snapshot.IndexedNode;
import com.firebase.client.Query;
import android.content.SharedPreferences.Editor;
import hello.peter.hello.Utils.Omni;
import android.app.PendingIntent;
import android.telephony.gsm.SmsManager;





public class SignUp extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageView;
    private TextView titleText;
    public Button submitButton;
    public EditText userName, passWord, phoneNumber;
    public String username, password, phonenumber, bitString = "";
    public Firebase Refs;
    public boolean inUse = true;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_sign_up);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(237, 24, 69));
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //super.onCreate(savedInstanceState);
        imageView = (ImageView) findViewById(R.id.imageView2);
        submitButton = (Button) findViewById(R.id.button3);
        userName = (EditText) findViewById(R.id.editText6);
        passWord = (EditText) findViewById(R.id.editText4);
        phoneNumber = (EditText) findViewById(R.id.editText3);
        //Add textWatcher on phone number to add dashes between 3rd and 7th number
        titleText = (TextView) findViewById(R.id.textView);
        Context context = getApplicationContext();
        editor = getSharedPreferences(getString(R.string.shared_prefs), MODE_PRIVATE).edit();


        //Listeners Below
        submitButton.setEnabled(true);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSubmitEnabled();
            }
        });

    }

    public void pickImage(View View) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    protected void checkSubmitEnabled(){
        username = userName.getText().toString();
        Firebase userRef = new Firebase("https://dazzling-fire-8069.firebaseio.com/users/").child(username);
        password = passWord.getText().toString();
        phonenumber = phoneNumber.getText().toString();
        final Firebase phoneRef = new Firebase("https://dazzling-fire-8069.firebaseio.com/numbers");
        if(username.length() == 0){
            Toast.makeText(getApplicationContext(), "Please input a UserName", Toast.LENGTH_LONG).show();
            return;
        }
        if(password.length() == 0){
            Toast.makeText(getApplicationContext(), "Please input a Password", Toast.LENGTH_LONG).show();
            return;
        }
        if(phonenumber.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please input a Phone Number", Toast.LENGTH_LONG).show();
            return;
        }
        if(password.toString().length() < 7){
            Toast.makeText(getApplicationContext(), "Password must be at least 7 characters", Toast.LENGTH_LONG).show();
            return;
        }


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "UserName in use, please choose another", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Add to Firebase
                    Firebase Ref = new Firebase("https://dazzling-fire-8069.firebaseio.com/");
                    Person newPerson = new Person(username, password, phonenumber, bitString);
                    Firebase newUser = Ref.child("users").child(username);
                    newUser.setValue(newPerson);
                    Firebase RefTest = new Firebase("https://dazzling-fire-8069.firebaseio.com/users").child(username);
                    Firebase newPath = RefTest.child("interests");
                    phoneRef.child(phonenumber).setValue(username);
                    //Adding logged in to shard prefs here
                    editor.putInt("Logged In", 1);
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putString("phonenumber", phonenumber);
                    editor.apply();

                    if(bitmap != null){saveProfilePic(bitmap);}
                    else{
                        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_default_profile);
                        saveProfilePic(bitmap);
                    }

                    Intent i = new Intent(SignUp.this, Interests.class);
                    //possibly change to sqlite later
                    SignUp.this.startActivity(i);
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            try {
                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(
                        data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                //This resizing works correctly
                //Bitmap resized = Bitmap.createScaledBitmap(bitmap,240,240,false);
                imageView.setImageBitmap(bitmap);
                bitString = Omni.BitMapToString(bitmap);
                titleText.setVisibility(View.INVISIBLE);
                stream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveProfilePic(Bitmap map){
        String filename = "bitmap.png";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
