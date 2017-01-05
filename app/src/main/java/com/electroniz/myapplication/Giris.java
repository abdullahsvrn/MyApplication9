package com.electroniz.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

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
    public static final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        giris=(Button)findViewById(R.id.giris);
        kaydol=(Button)findViewById(R.id.kaydol);
        edt_kul_id=(EditText)findViewById(R.id.edittexe_mail);
        edt_kul_sifre=(EditText)findViewById(R.id.edittexsifre);

        giris.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (!Objects.equals(edt_kul_id.getText().toString(), "") || !Objects.equals(edt_kul_sifre.getText().toString(), "")) {
                    new asyn_giris().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Kullanıcı ad Yada Kullanıcı şifre Boş Geçilemez!", Toast.LENGTH_LONG).show();
                }
            }
        });


        kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Giris.this, Kayit.class);

                startActivity(intent);


            }
        });


    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        SharedPreferences settings =getApplication().getSharedPreferences("kayıt", 0);
        if(settings.getString("userid",null)!=null){
            Intent intent = new Intent(Giris.this, MainActivity.class);
            startActivity(intent);
        }

    }
    public class asyn_giris extends AsyncTask<String,Void,String>{
        String jsonData;
        String kul_id;
        String  JSON_URLuyegiris;

        @Override
        protected void onPreExecute() {
            Timber.i("onPreExecute");
            final EditText edt_kul_id2=(EditText)findViewById(R.id.edittexe_mail);
            final EditText  edt_kul_sifre2=(EditText)findViewById(R.id.edittexsifre);

            JSON_URLuyegiris= "http://otak.somee.com/api/uyeGiris/"+edt_kul_id2.getText().toString().replace(" ","")+"/"+edt_kul_sifre2.getText().toString().replace(" ", "");
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
                    Timber.i("jsonData null degil ..");
                    Gson gson = new Gson();

                    kul_id = jsonData;
                    /*Arrays.asList(gson.fromJson(jsonData, String.class));*/
                    // L�STEY� KONTROL AMA�LI LOGCAT E YAZDIR


                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String result) {
            Timber.i("JSON VERİMİZ : > " + jsonData);
            Timber.i("JSON VERİMİZ : > " + kul_id);
           /* for(int i=0;i<kul_id.size();i++){*/
            if(!Objects.equals(kul_id, "null")) {
                Timber.i("JSON VERİMİZ : > " + kul_id);

                SharedPreferences settings =getApplication().getSharedPreferences("kayıt",0);
                SharedPreferences.Editor editor=settings.edit();
                editor.putString("userid",kul_id);
                editor.commit();

                Intent intent = new Intent(Giris.this, MainActivity.class);

                startActivity(intent);
            }
            else{
                yanlis=true;
                if(yanlis){
                    Toast.makeText(getApplicationContext(), "Kullanıcı ad Yada Kullanıcı şifre Yanlış!", Toast.LENGTH_LONG).show();
                }


             }

        }
    }


}