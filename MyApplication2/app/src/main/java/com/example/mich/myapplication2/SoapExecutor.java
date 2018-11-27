package com.example.mich.myapplication2;

import android.support.annotation.NonNull;

public class SoapExecutor extends Thread {

    private String imageString;
    private SoapCaller cs;
    private String x1,y1,x2,y2;

    public void run(){

        try{
            Thread.sleep(10);
            cs=new SoapCaller();
            imageString=cs.getRespomseFromServer( x1,y1,x2,y2);
            System.out.println("reached 2 \n");
        }catch(Exception ex)

        {
           ex.printStackTrace();
        }


    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public SoapExecutor(String x1, String y1, String x2, String y2) {
        super();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}