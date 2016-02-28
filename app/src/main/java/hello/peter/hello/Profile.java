package hello.peter.hello;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import hello.peter.hello.Utils.Omni;
import hello.peter.hello.Utils.RoundedAvatarDrawable;

public class Profile extends AppCompatActivity {

    public ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pic = (ImageView)findViewById(R.id.imageView4);
        //pic.setImageDrawable(new RoundedAvatarDrawable())
    }

}
