package com.electroniz.myapplication;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.google.gson.Gson;

        import java.io.IOException;
        import java.io.InputStream;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.Response;
        import timber.log.Timber;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imglogo;
    private String  JSON_URLkategori= "http://techhere.somee.com/api/KategoriApi/Kategoriler";
    private String  JSON_URLyeniurun= "http://techhere.somee.com/api/UrunApi/YeniUrunler";
    private OkHttpClient client = new OkHttpClient();

    TextView txt_urunfiyat;
    List<yeniurun> yeniuruns_gelen = new ArrayList<yeniurun>();

    ArrayList<String> arryUrunAd= new ArrayList<String>();
    ArrayList<String> arryUrunFiyat= new ArrayList<String>();
    ArrayList<String> arryUrunResim= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        imglogo=(ImageView)findViewById(R.id.sliderlogo);
     /*   imglogo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Main_donus_Fragment mainActivitye_donus=new Main_donus_Fragment();
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.relativelayoutfragment,mainActivitye_donus,mainActivitye_donus.getTag()).commit();

    }
    });*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Timber.i("onCreate ..");
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        new asyn_webservice().execute();






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.giris) {
            Intent intent = new Intent(MainActivity.this, profil.class);
            startActivity(intent);

            return true;
        }else if (id==R.id.sepet){
            return true;}

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        SharedPreferences settings =getApplication().getSharedPreferences("kayıt", 0);
        if(settings.getString("userid",null)==null){
            Intent intent = new Intent(MainActivity.this, Giris.class);
            startActivity(intent);
        }


    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.Telefon) {
            TelefonFragment telefonFragment=new TelefonFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayoutfragment,telefonFragment,telefonFragment.getTag()).commit();

        } else if (id == R.id.Bilgisayar) {
            BilgisayarFragment bilgisayarFragment=new BilgisayarFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayoutfragment,bilgisayarFragment,bilgisayarFragment.getTag()).commit();
        } else if (id == R.id.Bilgisayar_Parçaları) {
           BilgisayarParcalariFragment bilgisayarParcalariFragment=new BilgisayarParcalariFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayoutfragment,bilgisayarParcalariFragment,bilgisayarParcalariFragment.getTag()).commit();
        } else if (id == R.id.Foto_ve_Kamera) {
            FotoVeCameraFragment fotoVeCameraFragment=new FotoVeCameraFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayoutfragment,fotoVeCameraFragment,fotoVeCameraFragment.getTag()).commit();
        } else if (id == R.id.Tv_ve_Ev_Elektroniği) {
            TvVeEvElectFragment tvVeEvElectFragment=new TvVeEvElectFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayoutfragment,tvVeEvElectFragment,tvVeEvElectFragment.getTag()).commit();
        } else if (id == R.id.Ofis_Malzemeleri) {
            OfisMalzemeleriFragment ofisMalzemeleriFragment=new OfisMalzemeleriFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayoutfragment,ofisMalzemeleriFragment,ofisMalzemeleriFragment.getTag()).commit();
        } else if (id == R.id.Aksesuar) {
            AksesuarFragment aksesuarFragment=new AksesuarFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayoutfragment,aksesuarFragment,aksesuarFragment.getTag()).commit();
        } else if (id == R.id.Oyun_ve_Hobi) {
            OyunVeHobiFragment oyunVeHobiFragment=new OyunVeHobiFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayoutfragment,oyunVeHobiFragment,oyunVeHobiFragment.getTag()).commit();
        } else if (id == R.id.Ev_Aletleri) {
            EvAletleriFragment evAletleriFragment=new EvAletleriFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativelayoutfragment,evAletleriFragment,evAletleriFragment.getTag()).commit();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class asyn_webservice extends AsyncTask< Object,Void,Object> {
            String jsonData;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        Bitmap[] yeni_urunresimleri;


        List<kategori> kat = new ArrayList<kategori>();
        List<yeniurun> yeniuruns = new ArrayList<yeniurun>();

        LinearLayout yeniurungoruntule=(LinearLayout)findViewById(R.id.YeniUrunGoruntule);
        LinearLayout yeniurungoruntule_row;
        LinearLayout yeniurungoruntule_cloum;
        TextView txt_urunad;
        TextView txt_urunfiyat;
        ImageView img_yeniurun;
        Button btn_sepet_ekle;

        LinearLayout yeniurungoruntule_kalip_row=(LinearLayout)findViewById(R.id.row_lineer);
        LinearLayout yeniurungoruntule_kalip_cloum=(LinearLayout)findViewById(R.id.Cloum_layer);
        TextView txt_urunad_kalip=(TextView)findViewById(R.id.txt_urunad);
        TextView txt_urunfiyat_kalip=(TextView)findViewById(R.id.txt_urun_fiyat);
        ImageView img_yeniurun_kalip=(ImageView)findViewById(R.id.img_urun_resim);
        Button btn_sepet_ekle_kalip=(Button)findViewById(R.id.btn_sepet_ekle);


        @Override
        protected void onPreExecute() {
            Timber.i("onPreExecute");

        }

        @Override
        protected Object doInBackground(Object... params) {
            Timber.i("doInBackground ..");

            try {
                jsonData = run(JSON_URLkategori);
                Timber.i("JSON VERİMİZ : > " + jsonData);
                if (null != jsonData) {
                    Timber.i("jsonData null değil ..");
                    Gson gson = new Gson();

                    kat = Arrays.asList(gson.fromJson(jsonData, kategori[].class));

                    // LİSTEYİ KONTROL AMAÇLI LOGCAT E YAZDIR


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                    jsonData = run(JSON_URLyeniurun);
                Timber.i("JSON VERİMİZ : > " + jsonData);
                if (null != jsonData) {
                    Timber.i("jsonData null değil ..");
                    Gson gson = new Gson();

                    yeniuruns = Arrays.asList(gson.fromJson(jsonData, yeniurun[].class));
                    yeni_urunresimleri= new Bitmap[ yeniuruns.size()];
                    // LİSTEYİ KONTROL AMAÇLI LOGCAT E YAZDIR
                    for (int i = 0; i < yeniuruns.size(); i++) {



                        try {
                            InputStream in = new java.net.URL("http://techhere.somee.com/"+yeniuruns.get(i).urunresim).openStream();
                            yeni_urunresimleri[i]= BitmapFactory.decodeStream(in);
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage());
                            e.printStackTrace();
                        }


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
            if(yeniuruns.size()%2==1){tasdi=true;}
            for (int i = 0; i < yeniuruns.size(); i=i+2) {

                yeniurungoruntule_row=new LinearLayout(MainActivity.this);
                yeniurungoruntule_row.setLayoutParams(yeniurungoruntule_kalip_row.getLayoutParams());
                for(int j = 0; j<2; j++) {
                    if(tasdi==true){
                        if(i==yeniuruns.size()-1){

                            continue;
                        }
                    }
                    yeniurungoruntule_cloum = new LinearLayout(MainActivity.this);
                    txt_urunad = new TextView(MainActivity.this);
                    txt_urunfiyat = new TextView(MainActivity.this);
                    img_yeniurun = new ImageView(MainActivity.this);
                    btn_sepet_ekle=new Button(MainActivity.this);

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
                }

                if(tasdi==true){
                    if(i==yeniuruns.size()-1){

                        continue;
                    }
                }

                yeniurungoruntule_row.setVisibility(View.VISIBLE);
                yeniurungoruntule.addView(yeniurungoruntule_row);
            }
            for (int i = 0; i < kat.size(); i++) {
                Timber.i("katad #" + i + " : > " + kat.get(i).kat_ad);
                Timber.i("katid #" + i + " : > " + kat.get(i).kat_id);
                Timber.i("anakatid #" + i + " : > " + kat.get(i).anakat_id);
                menu.add(R.id.group1, Menu.NONE, Menu.NONE, kat.get(i).kat_ad);


            }



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