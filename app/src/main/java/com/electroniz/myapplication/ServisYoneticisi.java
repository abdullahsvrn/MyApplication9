package com.electroniz.myapplication;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by ABDULLAH on 6.11.2016.
 */
public class ServisYoneticisi {


    private static final String metotadi="kontrol";
    private static final String namespace="http://tumpuri.org/";
    private static final String soap_action="http://tempuri.org/kontrol";
    private static  final String url="http://192.52.242.121:4247/webservice_deneme.asmx";

    SoapObject soapObject;
    SoapSerializationEnvelope soapSerializationEnvelope;
    HttpTransportSE httpTransportSE;

    public void veri(String ad,String sifre){

        soapObject=new SoapObject(namespace,metotadi);
        soapObject.addProperty("id",ad);
        soapObject.addProperty("key",sifre);
        soapSerializationEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapSerializationEnvelope.dotNet=true;
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        httpTransportSE =new HttpTransportSE(url);
        httpTransportSE.debug=true;

        try {
            httpTransportSE.call(soap_action, soapSerializationEnvelope);
            SoapPrimitive soapPrimitive=(SoapPrimitive)soapSerializationEnvelope.getResponse();
            System.out.println(soapPrimitive.toString());

        }catch (Exception ex){

            ex.printStackTrace();
        }

    }

}
