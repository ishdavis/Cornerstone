package hello.peter.hello;

import android.database.Cursor;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import java.io.FileNotFoundException;
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
import android.widget.Toast;
import android.os.AsyncTask;
import java.sql.*;
import java.lang.*;
import android.graphics.Color;

public class SignUp extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageView;
    public Button submitButton;
    public EditText userName, passWord, phoneNumber;
    public String username, password, phonenumber;

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
        password = passWord.getText().toString();
        phonenumber = phoneNumber.getText().toString();
        if(username.length() == 0){
            Toast.makeText(getApplicationContext(), "Please input a UserName", Toast.LENGTH_LONG).show();
            return;
        }
        if(password.toString().length() == 0){
            Toast.makeText(getApplicationContext(), "Please input a Password", Toast.LENGTH_LONG).show();
            return;
        }
        if(phonenumber.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please input a Phone Number", Toast.LENGTH_LONG).show();
            return;
        }
        Intent i = new Intent(SignUp.this, Interests.class);
        SignUp.this.startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            try {
                // We need to recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(
                        data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                ExifInterface attempt = new ExifInterface(tempUri.getPath());
                int orientation = attempt.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                flipImage(orientation);
                stream.close();
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return retVal;
    }

    public void flipImage(int orientation) {
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                bitmap = rotateImage(bitmap, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                bitmap = rotateImage(bitmap, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                bitmap = rotateImage(bitmap, 270);
                break;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    /*public class Connect extends AsyncTask<Context, Integer, Long> {
        protected Long doInBackground(Context... contexts) {
            Connection connection;
            String query = "Some query";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://<your cloud IP address>/<database schema you want to connect to>", "<user>", "<password>");

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        }
    }}*/

}
