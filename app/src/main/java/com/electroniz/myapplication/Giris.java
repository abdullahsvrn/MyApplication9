package com.electroniz.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;



public class Giris extends AppCompatActivity {
Button kaydol,giris;
    EditText edt_kul_id,edt_kul_sifre;
    private String edt_kul_id_,edt_kul_sifre_;
    private OkHttpClient client = new OkHttpClient();
    Boolean yanlis=false;
    SoapObject soapObject;
    SoapSerializationEnvelope soapSerializationEnvelope;
    HttpTransportSE httpTransportSE;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        giris=(Button)findViewById(R.id.giris);
        kaydol=(Button)findViewById(R.id.kaydol);
        edt_kul_id=(EditText)findViewById(R.id.edittexe_mail);
        edt_kul_sifre=(EditText)findViewById(R.id.edittexsifre);


        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( edt_kul_id.getText().toString()!="" || edt_kul_sifre.getText().toString()!="") {
                    edt_kul_id_ = edt_kul_id.getText().toString();
                    edt_kul_sifre_ = edt_kul_sifre.getText().toString();
                    new asyn_giris().execute();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Kullanıcı İd Yada Kullanıcı Şifre Boş Geçilemez!", Toast.LENGTH_LONG).show();
                }
            }
        });
        if(yanlis){
            Toast.makeText(getApplicationContext(), "Kullanıcı İd Yada Kullanıcı Şifre Yanlış!", Toast.LENGTH_LONG).show();
        }
        kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Giris.this, Kayit.class);

                startActivity(intent);


            }
        });
    }

    public class asyn_giris extends AsyncTask<String,Void,String>{
        String jsonData;
        String kul_id;
        String  JSON_URLuyegiris;

        @Override
        protected void onPreExecute() {
            Timber.i("onPreExecute");
            JSON_URLuyegiris= "http://techhere.somee.com/api/UyeApi/GirisYap?nick="+edt_kul_id_+"&parola="+edt_kul_sifre_;
        }
        private String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        @Override
        protected String doInBackground(String... params) {
            try {

                jsonData = run(JSON_URLuyegiris);


                if (null != jsonData) {
                    Timber.i("jsonData null değil ..");
                    Gson gson = new Gson();

                    kul_id = jsonData;
                    /*Arrays.asList(gson.fromJson(jsonData, String.class));*/
                    // LİSTEYİ KONTROL AMAÇLI LOGCAT E YAZDIR


                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            Timber.i("JSON VERİMİZ : > " + jsonData);
            Timber.i("JSON VERİMİZ : > " + kul_id);
           /* for(int i=0;i<kul_id.size();i++){*/
            if(kul_id!="null" || kul_id!="NULL" || kul_id!="Null" || kul_id!=null) {
                Timber.i("JSON VERİMİZ : > " + kul_id);
                /*Intent intent = new Intent(Giris.this, MainActivity.class);

                startActivity(intent);*/
            }
            else{
                yanlis=true;



             }
           /* }*/
        }
    }


}
