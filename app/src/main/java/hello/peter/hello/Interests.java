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

public class Interests extends ListActivity {

    public ListView chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(237, 24, 69));
        chart = (ListView) findViewById(android.R.id.list);
        String [] words = {"Hello", "World", "what", "a", "b", "c", "d", "e", "i", "aaaa", "ieiei"};
        //this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, android.R.id.list, words));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, words);
        setListAdapter(adapter);
    }

}
