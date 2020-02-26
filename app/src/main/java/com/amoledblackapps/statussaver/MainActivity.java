package com.amoledblackapps.statussaver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amoledblackapps.statussaver.Adapters.MyAdapter;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager llm;
    private int permCode = 1;
    private InterstitialAd interstitialAd;
    private InterstitialAd interstitialAd2;
    private InterstitialAd interstitialAd3;
    private AdView adView;
    private CountDownTimer timer;


    public final String WHATSAPP_DIR_LOCATION = "/WhatsApp/Media/.Statuses";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);


       if (item.getItemId() == R.id.share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out this app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        return true;
    }



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        File file = new File(Environment.getExternalStorageDirectory().toString()+ WHATSAPP_DIR_LOCATION);
        MyAdapter recyclerAdapter = new MyAdapter(this.getListFiles(file),MainActivity.this);
        recyclerView.setAdapter(recyclerAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        storagePerm();

        AudienceNetworkAds.initialize(this);
        loadBanner();
        interstitialAd3 = new InterstitialAd(this, getResources().getString(R.string.interstitial));
        interstitialAd3.setAdListener(new InterstitialAdListener() {

            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

                interstitialAd3.destroy();

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }


            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd3.show();

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

        });

        timer = new CountDownTimer(50000, 20) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                try {
                    interstitialAd3.loadAd();
                } catch (Exception e) {
                    Log.e("Error", "Error: " + e.toString());
                }
            }
        }.start();





        interstitialAd2 = new InterstitialAd(this, getResources().getString(R.string.interstitial));
        interstitialAd2.setAdListener(new InterstitialAdListener() {

            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

                interstitialAd2.destroy();

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }


            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd2.show();

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

        });
        interstitialAd2.loadAd();

        interstitialAd = new InterstitialAd(this, getResources().getString(R.string.interstitial));
        interstitialAd.setAdListener(new InterstitialAdListener() {

            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

               interstitialAd.destroy();

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }


            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd.show();

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

        });


            timer = new CountDownTimer(28000, 20) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    try {
                        interstitialAd.loadAd();
                    } catch (Exception e) {
                        Log.e("Error", "Error: " + e.toString());
                    }
                }
            }.start();
        }

    private void storagePerm() {

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {



        } else {


            requestStoragePermission();


        }
    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to access WhatsApp statuses")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(MainActivity.this ,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},permCode );

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();


        } else{

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},permCode );



        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == permCode){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void  loadBanner()
    {

        adView = new AdView(this, getResources().getString(R.string.banner), AdSize.BANNER_HEIGHT_50);

        LinearLayout adContainer = findViewById(R.id.banner_container);

        adContainer.addView(adView);

        adView.loadAd();
    }


    private ArrayList<File> getListFiles(File parentDir)
    {

        ArrayList<File> inFiles = new ArrayList<>();
        File[] files;

        files = parentDir.listFiles();

        if (files != null)
        {
            for(File file : files)
            {
                if (file.getName().endsWith(".jpg") ||

                file.getName().endsWith(".gif") ||
                        file.getName().endsWith(".mp4"))
                {

                    if (!inFiles.contains(file))
                        inFiles.add(file);
                }
            }
        }

        return inFiles;
    }
}
