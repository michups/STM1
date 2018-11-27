package com.example.mich.myapplication2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.serialization.PropertyInfo;

import android.os.AsyncTask;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    public final Float  longtitudeMAX = 51.4167f;
    public final Float longtitudeMIN =  51.40892f;
    public final Float  longtitudeSize = longtitudeMAX-longtitudeMIN;
    public final Float latitudeMAX = 21.15f;
    public final Float latitudeMIN = 21.136117f;
    public final Float latitudeSize = latitudeMAX-latitudeMIN;
    public MutableFloat downx;
    public MutableFloat downy;
    public MutableFloat upx ;
    public MutableFloat upy ;
    public String result;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downx = new MutableFloat();
        downy = new MutableFloat();
        upx = new MutableFloat();
        upy = new MutableFloat();
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView);
//        imageView1.setOnDragListener();
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "The favorite list would appear on clicking this icon",
                        Toast.LENGTH_LONG).show();
            }
        });

        imageView1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                EditText leftUpX= (EditText) findViewById(R.id.leftUpX);
                EditText leftUpY= (EditText) findViewById(R.id.leftUpY);
                EditText rightDownX= (EditText) findViewById(R.id.rightDownX);
                EditText rightDownY= (EditText) findViewById(R.id.rightDownY);
                ImageView imageView=(ImageView) findViewById(R.id.imageView2);
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    Log.d("TouchTest", "Touch down");
                    downx.setValue(event.getX());
                    downy.setValue(event.getY());
                    System.out.println(downx.toString());
                    System.out.println(downy.toString());
                    leftUpX.setText(""+(downx.getValue()/imageView.getWidth()*longtitudeSize +51.40892f));
                    leftUpY.setText(""+(downy.getValue()/imageView.getHeight()*latitudeSize +21.136117f));
                    updateCoordinates(downx, downy, upx, upy, leftUpX, leftUpY, rightDownX, rightDownY, imageView, view);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    upx.setValue(event.getX());
                    upy.setValue(event.getY());
                    System.out.println(upx.toString());
                    System.out.println(upy.toString());
                    updateCoordinates(downx, downy, upx, upy, leftUpX, leftUpY, rightDownX, rightDownY, imageView, view);
                }  else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                Log.d("TouchTest", "Touch up");
                    upx.setValue(event.getX());
                    upy.setValue(event.getY());
                    updateCoordinates(downx, downy, upx, upy, leftUpX, leftUpY, rightDownX, rightDownY, imageView, view);
            }
                return true;
            }
        });
    }

    public void updateCoordinates(MutableFloat downx, MutableFloat downy, MutableFloat upx, MutableFloat upy,
                                  EditText leftUpX, EditText leftUpY, EditText rightDownX, EditText rightDownY,
                                  ImageView imageView, View view ){

        if(upx.getValue()>imageView.getWidth())
            upx.setValue(new Float(imageView.getWidth()));
        if(downx.getValue()>imageView.getWidth())
            downx.setValue(new Float(imageView.getWidth()));
        if(upx.getValue()<0)
            upx.setValue(0f);
        if(downx.getValue()<0)
            downx.setValue(0f);

        if(upy.getValue()>imageView.getHeight())
            upy.setValue((float)imageView.getWidth());
        if(downy.getValue()>imageView.getHeight())
            downy.setValue((float)imageView.getWidth());
        if(upy.getValue()<0)
            upy.setValue(0f);
        if(downy.getValue()<0)
            downy.setValue(0f);

        if(upx.getValue()<=downx.getValue()){

            leftUpX.setText(""+(upx.getValue()/imageView.getWidth()*longtitudeSize +51.40892f));
            rightDownX.setText(""+(downx.getValue()/imageView.getWidth()*longtitudeSize +51.40892f));

        }else{

            leftUpX.setText(""+(downx.getValue()/imageView.getWidth()*longtitudeSize +51.40892f));
            rightDownX.setText(""+(upx.getValue()/imageView.getWidth()*longtitudeSize +51.40892f));
        }

        if(upy.getValue()<=downy.getValue()){

            leftUpY.setText(""+(upy.getValue()/imageView.getHeight()*latitudeSize +21.136117f));
            rightDownY.setText(""+(downy.getValue()/imageView.getHeight()*latitudeSize +21.136117f));

        }else{

            leftUpY.setText(""+(downy.getValue()/imageView.getHeight()*latitudeSize +21.136117f));
            rightDownY.setText(""+(upy.getValue()/imageView.getHeight()*latitudeSize +21.136117f));
        }


        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
//                    imageView.draw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(100,0,0,100));
        canvas.drawRect(downx.getValue(),upy.getValue(),upx.getValue(),downy.getValue(), paint);
        imageView.setImageBitmap(bitmap);
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {

        String message =getImageBitmapString();
        DataHolder extras = DataHolder.getInstance();
        extras.putExtra("extra",message);

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.leftUpX);
//        intent.putExtra(A, message);


        startActivity(intent);
    }

    public String getImageBitmapString(){
        try{

            EditText leftUpX= (EditText) findViewById(R.id.leftUpX);
            EditText leftUpY= (EditText) findViewById(R.id.leftUpY);
            EditText rightDownX= (EditText) findViewById(R.id.rightDownX);
            EditText rightDownY= (EditText) findViewById(R.id.rightDownY);
            // condition for keeping the main thread asleep
//            thread = "START";

            // create a new webservice caller thread
            SoapExecutor c = new SoapExecutor(leftUpX.getText().toString(),
                    leftUpY.getText().toString(),
                    rightDownX.getText().toString(),
                    rightDownY.getText().toString());

            // join the new thread, start it and then select what webservice you want to access
            c.join(); c.start();

            // keep this thread asleep while the service thread is running
            while(c.isAlive())
            {
                try
                {
                    Thread.sleep(10);
                }
                catch(Exception e)
                {
                   e.printStackTrace();
                }
            }
            System.out.println(c.getImageString());
            return c.getImageString();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }



}