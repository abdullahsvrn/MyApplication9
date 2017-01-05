package com.electroniz.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class kisibilgileri extends AppCompatActivity {

    private String  JSON_URLyeniurun= "http://techhere.somee.com/api/UrunApi/YeniUrunler";
    EditText edt_ad,edt_soyad,edt_dtarih,edt_mail,edt_cinsiyet,edt_sifre1,edt_sifre2,edt_tel;
    Button btngüncel;
    ImageView img_profil;
    ArrayList<String> kisibilgileri= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisibilgileri);




    }
}
