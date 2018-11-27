package com.example.mich.myapplication2;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SoapCaller {

    public final Float  longtitudeMAX = 51.4167f;
    public final Float longtitudeMIN =  51.40892f;
    public final Float latitudeMAX = 21.15f;
    public final Float latitudeMIN = 21.136117f;
    String NAMESPACE = "http://stm.com/";
    String URL="http://192.168.203.1:8080/mavenproject1/NewWebService?WSDL";
    final String METHOD_NAME = "getMap";
    final String SOAP_ACTION =  "http://stm.com/getMap";

    public String getRespomseFromServer( String x1, String y1, String x2, String y2)
    {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("longitude1", longtitudeMIN.toString());
        request.addProperty("latitude1", latitudeMIN.toString());
        request.addProperty("longitude2", longtitudeMAX.toString());
        request.addProperty("latitude2", latitudeMAX.toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        Log.d("BBBBBBBBBBBB", request.toString());

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);

            SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();


            Log.d("result",resultsRequestSOAP.toString());
            return  resultsRequestSOAP.toString();

        } catch (Exception e) {

            e.printStackTrace();
//            for (int i = 0; i < e.getStackTrace().length; i++) {
//                Log.d("AAAAAAAAAAA", e.getStackTrace()[i].toString());
//
//            }
        }
        return null;

    }
}