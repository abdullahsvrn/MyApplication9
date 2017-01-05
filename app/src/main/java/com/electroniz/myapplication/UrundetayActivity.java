package com.electroniz.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class UrundetayActivity extends AppCompatActivity {
    private String  JSON_URLurundetay= "http://otak.somee.com/api/UrunGetir/";
    private OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urundetay);
        Bundle bundle=getIntent().getExtras();
        int asd=bundle.getInt("intent");
        JSON_URLurundetay=JSON_URLurundetay+asd;
        new asyn_webservice().execute();
    }
    public class asyn_webservice extends AsyncTask< Object,Void,Object> {
        String jsonData;


        Bitmap[] yeni_urunresimleri;



        urungetir yeniuruns = new urungetir();

        LinearLayout yeniurungoruntule=(LinearLayout)findViewById(R.id.urunozelliks);
        LinearLayout yeniurungoruntule_cember;
        LinearLayout yeniurungoruntule_row;
        LinearLayout yeniurungoruntule_cloum;
        TextView txt_urunad=(TextView) findViewById(R.id.txturunad);
        TextView txt_urunfiyat=(TextView) findViewById(R.id.txturunfiyat);
        ImageView img_yeniurun=(ImageView) findViewById(R.id.imgurunresim);
        Button btn_sepet_ekle=(Button) findViewById(R.id.btnsepet);


        LinearLayout urunozelliks_kalip=(LinearLayout)findViewById(R.id.urunozelliks_kalip);
        LinearLayout yeniurungoruntule_kalip_row=(LinearLayout)findViewById(R.id.urunozellikleri_tip_kalip);
        LinearLayout yeniurungoruntule_kalip_cloum=(LinearLayout)findViewById(R.id.urunozellik_deger_kalip);



        @Override
        protected void onPreExecute() {
            Timber.i("onPreExecute");

        }

        @Override
        protected Object doInBackground(Object... params) {
            Timber.i("doInBackground ..");


            try {
                jsonData = run(JSON_URLurundetay);
                Timber.i("JSON VERİMİZ : > " + jsonData);
                if (null != jsonData) {
                    Timber.i("jsonData null değil ..");
                    Gson gson = new Gson();

                    yeniuruns =gson.fromJson(jsonData, urungetir.class);
                    yeni_urunresimleri= new Bitmap[1];
                    // LİSTEYİ KONTROL AMAÇLI LOGCAT E YAZDIR




                        try {
                            InputStream in = new java.net.URL("http://techhere.somee.com/"+yeniuruns.urunresim_url).openStream();
                            yeni_urunresimleri[0]= BitmapFactory.decodeStream(in);
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage());
                            e.printStackTrace();
                        }




                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            Timber.i("onPostExecute ..");
            boolean tasdi=false;


            img_yeniurun.setImageBitmap(yeni_urunresimleri[0]);
            img_yeniurun.setMinimumWidth(1000);
            img_yeniurun.setMinimumHeight(600);
            txt_urunad.setText(yeniuruns.urun_ad);
            txt_urunad.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txt_urunfiyat.setText(yeniuruns.urun_fiyat);
            txt_urunfiyat.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txt_urunfiyat.setTextColor(Color.parseColor("#039BE5"));
            btn_sepet_ekle.setText("Sepete Ekle");








            /*

                yeniurungoruntule_cember=new LinearLayout(UrundetayActivity.this);
                yeniurungoruntule_cember.setLayoutParams(urunozelliks_kalip.getLayoutParams());

                yeniurungoruntule_row=new LinearLayout(UrundetayActivity.this);
                yeniurungoruntule_row.setLayoutParams(yeniurungoruntule_kalip_row.getLayoutParams());

                yeniurungoruntule_cloum=new LinearLayout(UrundetayActivity.this);
                yeniurungoruntule_cloum.setLayoutParams(yeniurungoruntule_kalip_cloum.getLayoutParams());



                    yeniurungoruntule_cloum = new LinearLayout(UrundetayActivity.this);
                    txt_urunad = new TextView(UrundetayActivity.this);
                    txt_urunfiyat = new TextView(UrundetayActivity.this);
                    img_yeniurun = new ImageView(UrundetayActivity.this);
                    btn_sepet_ekle=new Button(UrundetayActivity.this);

                    yeniurungoruntule_cloum.setLayoutParams(yeniurungoruntule_kalip_cloum.getLayoutParams());
                    //img_yeniurun.setLayoutParams(img_yeniurun_kalip.getLayoutParams());
                    txt_urunad.setLayoutParams(txt_urunad_kalip.getLayoutParams());
                    txt_urunfiyat.setLayoutParams(txt_urunfiyat_kalip.getLayoutParams());
                    btn_sepet_ekle.setLayoutParams(btn_sepet_ekle_kalip.getLayoutParams());

                    yeniurungoruntule_cloum.setOrientation(LinearLayout.VERTICAL);
                    yeniurungoruntule_cloum.setWeightSum(1);



                    img_yeniurun.setImageBitmap(yeni_urunresimleri[i + j]);
                    img_yeniurun.setMinimumWidth(1000);
                    img_yeniurun.setMinimumHeight(600);
                    txt_urunad.setText(yeniuruns.get(i + j).urun_ad);
                    txt_urunad.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    txt_urunfiyat.setText(yeniuruns.get(i + j).urun_fiyat);
                    txt_urunfiyat.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    txt_urunfiyat.setTextColor(Color.parseColor("#039BE5"));
                    btn_sepet_ekle.setText("Sepete Ekle");

                    yeniurungoruntule_cloum.addView(img_yeniurun);
                    yeniurungoruntule_cloum.addView(txt_urunad);
                    yeniurungoruntule_cloum.addView(txt_urunfiyat);
                    yeniurungoruntule_cloum.addView(btn_sepet_ekle);


                    yeniurungoruntule_row.addView(yeniurungoruntule_cloum);



                yeniurungoruntule_row.setVisibility(View.VISIBLE);
                yeniurungoruntule.addView(yeniurungoruntule_row);

*/


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
