package com.example.mich.myapplication2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.mich.myapplication2.MainActivity.EXTRA_MESSAGE;

public class DisplayMessageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        String imageInString = "";

        DataHolder extras = DataHolder.getInstance();
        if (extras.hasExtra("extra")) {
            imageInString = (String) extras.getExtra("extra");
        }



        byte[] data = Base64.decode(imageInString, Base64.DEFAULT);
        try {
            // Convert bytes data into a Bitmap
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            ImageView imageView = new ImageView(DisplayMessageActivity.this);
// Set the Bitmap data to the ImageView
            imageView.setImageBitmap(bmp);

// Get the Root View of the layout
            ViewGroup layout = (ViewGroup) findViewById(android.R.id.content);
// Add the ImageView to the Layout
            layout.addView(imageView);

            String text = new String(data, "UTF-8");
        }catch (Exception e ){
            e.printStackTrace();
        }
        System.out.println("AAAAAAAAAAAAA"+imageInString);
        // Capture the layout's TextView and set the string as its text
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(imageInString);
    }


}
