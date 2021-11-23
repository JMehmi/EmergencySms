package com.mehmi.kumar.emergencysms;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.awt.font.NumericShaper;

public class MainActivity extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText Numero = (EditText) findViewById(R.id.Numero);//getting the number and setting the number
        final EditText Messaggio = (EditText) findViewById(R.id.Messaggio);// getting and setting the text messafe
        Button invia = (Button) findViewById(R.id.button);
        final String[] m = {""};
        final LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        //Getting the location of the phone
        final LocationListener l = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                m[0] ="http://maps.google.com/maps?saddr=" + location.getLatitude() + "," + location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        //checking if location permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{

                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
            return;
        } else {
            //If it's granted on the clicking of the button sned the message and show a toast
            lm.requestLocationUpdates("gps", 5000, 0, l);
            invia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String n = Numero.getText().toString();
                    String mex=Messaggio.getText().toString();
                    mex+="/n"+m[0];

                    try {
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(n, null, mex, null, null);
                        Toast.makeText(MainActivity.this, "Spedito", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Non spedito", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

    }

    public void onRequestPermissionResult(int requestCode, String[] permission, int[] granResults) {
        switch (requestCode) {
            case 10:
                if (granResults.length > 0 && granResults[0] == PackageManager.PERMISSION_GRANTED)
                    //
                    return;
        }

    }
}








