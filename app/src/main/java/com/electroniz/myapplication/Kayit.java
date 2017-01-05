package com.electroniz.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

public class Kayit extends AppCompatActivity {
    EditText ad,soyad;
    ScrollView scrollView;
Button kayit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        kayit = (Button) findViewById(R.id.kayit);

        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kayit.this, MainActivity.class);

                startActivity(intent);


            }
        });
        final EditText ad = (EditText) findViewById(R.id.ad);
        scrollView=(ScrollView)findViewById(R.id.scrollView);
        ad.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {




            }}

        });


}}