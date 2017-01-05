package com.electroniz.myapplication;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class Profilbilgilerim extends AppCompatActivity {

    EditText txtad, txtsoyad, txtnick, txtmail, txtcinsiyet, txtsehir, txttel;
    ImageView imgprofilresim;
    Button btn_guncelle;
    private String JSON_URLuye;
    public static String acıklama;
    private OkHttpClient client = new OkHttpClient();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilbilgilerim);
        new asyn_webservice().execute();

        btn_guncelle = (Button) findViewById(R.id.guncelle);

        btn_guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           new asyn_webserguncelle(acıklama).execute(acıklama);

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Profilbilgilerim Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.electroniz.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client2, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Profilbilgilerim Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.electroniz.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client2, viewAction);
        client2.disconnect();
    }

    public class asyn_webservice extends AsyncTask<Object, Void, Object> {
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
                    InputStream in = new URL(urlRE).openStream();

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

        @Override
        protected void onPostExecute(Object result) {


            txtad = (EditText) findViewById(R.id.ad);
            txtsoyad = (EditText) findViewById(R.id.soyad);
            txtnick = (EditText) findViewById(R.id.nick);
            txtmail = (EditText) findViewById(R.id.email);
            txtcinsiyet = (EditText) findViewById(R.id.cinsiyet);
            txtsehir = (EditText) findViewById(R.id.sehir);
            txttel = (EditText) findViewById(R.id.tel);
            imgprofilresim = (ImageView) findViewById(R.id.imgprofil);
            txtad.setText(kisiprofil.User_Ad.replace(" ", ""));
            txtsoyad.setText(kisiprofil.User_Soyad.replace(" ", ""));
            txtnick.setText(kisiprofil.User_id.replace(" ", ""));
            txtmail.setText(kisiprofil.User_email.replace(" ", ""));
            txtcinsiyet.setText(kisiprofil.User_cinsiyet.replace(" ", ""));
            txtsehir.setText(kisiprofil.User_Sehir.replace(" ", ""));
            txttel.setText(kisiprofil.User_tel.replace(" ", ""));
            imgprofilresim.setImageBitmap(bitt[0]);
            acıklama=kisiprofil.User_bilgileri;

        }
    }

    public class asyn_webserguncelle extends AsyncTask<Object, String, Object> {
        String acc;
        public asyn_webserguncelle(String acc){
           this.acc=acc;
        }

        String jsonData, kldf,cevap,accc;

        protected void onPreExecute() {
            SharedPreferences settings = getApplication().getSharedPreferences("kayıt", 0);
            SharedPreferences.Editor editor = settings.edit();
            kldf = settings.getString("userid", null);
            accc=acc;
            txtad = (EditText) findViewById(R.id.ad);
            txtsoyad = (EditText) findViewById(R.id.soyad);
            txtnick = (EditText) findViewById(R.id.nick);
            txtmail = (EditText) findViewById(R.id.email);
            txtcinsiyet = (EditText) findViewById(R.id.cinsiyet);
            txtsehir = (EditText) findViewById(R.id.sehir);
            txttel = (EditText) findViewById(R.id.tel);
            JSON_URLuye = "http://www.otak.somee.com/api/uyeGuncelle/"+
                    kldf.replace(" ", "").replace("\"","")+"/"+
                    txtnick.getText()+"/"+
                    txtad.getText()+"/"+
                    txtsoyad.getText()+"/"+
                    txtsehir.getText()+"/"+
                    txttel.getText()+"/"+
                    txtcinsiyet.getText()+"/"+
                    accc;

        }
        @Override
        protected Object doInBackground(Object... params) {

            try {
                jsonData = run(JSON_URLuye);
                Timber.i("JSON VERİMİZ : > " + jsonData);
                if (null != jsonData) {
                    Timber.i("jsonData null değil ..");
                    Gson gson = new Gson();

                    cevap = jsonData;

                    // LİSTEYİ KONTROL AMAÇLI LOGCAT E YAZDIR


                }
            } catch (IOException e) {
                cevap=e.toString();
            }


            return null;
        }
        @TargetApi(Build.VERSION_CODES.KITKAT)
        protected void onPostExecute(Object result) {
            if(Objects.equals(cevap, "\"OLdu!\"")){

                Toast.makeText(getApplicationContext(), "Bilgileriniz Güncellendi!",Toast.LENGTH_LONG).show();
            }
            else{ Toast.makeText(getApplicationContext(), "Bilgileriniz Güncellenmedi!", Toast.LENGTH_LONG).show();}
        }
        private String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        }
    }
}

