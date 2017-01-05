package com.electroniz.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class profil extends AppCompatActivity {
Button btn_profilbil,btn_sepetim,btn_cikis;
    private String  JSON_URLuye;
    private OkHttpClient client = new OkHttpClient();

    ImageView imgprofilresim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        new asyn_webservice().execute();





        btn_profilbil = (Button) findViewById(R.id.btnprofilbilgileri);

        btn_profilbil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profil.this, Profilbilgilerim.class);

                startActivity(intent);


            }
        });
        btn_sepetim = (Button) findViewById(R.id.btnsepetim);

        btn_sepetim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profil.this, SepetActivity.class);

                startActivity(intent);


            }
        });
        btn_cikis = (Button) findViewById(R.id.btn_cikis);

        btn_cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences settings =getApplication().getSharedPreferences("kayıt", 0);
                SharedPreferences.Editor editor=settings.edit();
                editor.remove("userid");
                editor.commit();
                Intent intent = new Intent(profil.this, Giris.class);

                startActivity(intent);


            }
        });

    }
    public class asyn_webservice extends AsyncTask< Object,Void,Object> {
        String jsonData, kldf;
        Bitmap[] bitt;
        public profilbilgilerimveri kisiprofil;

        @Override
        protected Object doInBackground(Object... params) {
            Timber.i("doInBackground ..");
            SharedPreferences settings = getApplication().getSharedPreferences("kayıt", 0);
            SharedPreferences.Editor editor = settings.edit();
            kldf = settings.getString("userid", null);

            JSON_URLuye = "http://otak.somee.com/api/uyeBilgisi/" + kldf.replace("\"", "");
            try {
                jsonData = run(JSON_URLuye);
                Timber.i("JSON VERİMİZ : > " + jsonData);
                if (null != jsonData) {
                    Timber.i("jsonData null değil ..");
                    Gson gson = new Gson();

                    kisiprofil = gson.fromJson(jsonData, profilbilgilerimveri.class);
                    bitt = new Bitmap[1];

                    String urlRE = "http://otak.somee.com/Userresimler/" + kisiprofil.User_fotograf_url.replace(" ", "");
                    InputStream in = new java.net.URL(urlRE).openStream();

                    bitt[0] = BitmapFactory.decodeStream(in);
                    // LİSTEYİ KONTROL AMAÇLI LOGCAT E YAZDIR


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "asdf85";
        }

        private String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        }
        protected void onPostExecute(Object result) {
            imgprofilresim=(ImageView)findViewById(R.id.imgprofil1);
            imgprofilresim.setImageBitmap(bitt[0]);
        }
    }
}
