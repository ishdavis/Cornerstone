package hello.peter.hello;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import com.firebase.client.Firebase;
import android.util.Base64;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import hello.peter.hello.Models.Person;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    TextView mainTextView;
    Button mainButton;
    Button b1,b2;
    TextView tx1;
    EditText ed1,ed2;
    int counter = 3;
    int start = 9;
    private static final String PREFS = "prefs";
    private static final String PREF_NAME = "name";
    SharedPreferences prefs;
    String userName, otherName, hello;
    public ProgressDialog barProgressDialog;
    public Handler updateBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(237, 24, 69));
        Firebase.setAndroidContext(this);
        prefs = getSharedPreferences(getString(R.string.shared_prefs), MODE_PRIVATE);
        if(prefs.getInt("Logged In", 0) == 1){
            //User is logged in, go to their profile here
        }
        b1=(Button)findViewById(R.id.button);
        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);
        b2=(Button)findViewById(R.id.button2);
        updateBarHandler = new Handler();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = ed1.getText().toString(), password = ed2.getText().toString();
                if (username.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter a UserName", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter a Password", Toast.LENGTH_LONG).show();
                    return;
                }
                final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "Checking....", true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Firebase userRef = new Firebase("https://dazzling-fire-8069.firebaseio.com/users/" + username).child("password");
                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        String pass = (String) snapshot.getValue();
                                        if (pass.equals(password)) {
                                            //go to new activity, specifically profile
                                            dialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                        } else {
                                            dialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Incorrect UserName or Password", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Incorrect UserName or Password", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError arg0) {
                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                }).start();

                /*SharedPreferences.Editor e = mSharedPreferences.edit();
                e.putString(PREF_NAME, inputName);
                e.commit();*/
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                MainActivity.this.startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

}
