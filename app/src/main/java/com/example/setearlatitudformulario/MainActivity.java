package com.example.setearlatitudformulario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.hardware.location.*;

public class MainActivity extends AppCompatActivity {

    EditText x, y;
    double latUsuario, lonUsuario;
    Button aceptar;
    Boolean Estado = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }

        x = (EditText)findViewById(R.id.editTextX);
        y = (EditText)findViewById(R.id.editTextY);

        aceptar = (Button)findViewById(R.id.boton);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);

                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        if(Estado)
                        {
                            latUsuario = (double) location.getLatitude();
                            lonUsuario = (double) location.getLongitude();
                        }
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                    public void onProviderEnabled(String provider) {}

                    public void onProviderDisabled(String provider) {}
                };

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                //



                String coordX = x.getText().toString();
                String coordY = y.getText().toString();



                try {
                    Double.parseDouble(coordX);
                    Double.parseDouble(coordY);

                    Intent i = new Intent(MainActivity.this, MapsActivity.class);
                    i.putExtra("x", coordX);
                    i.putExtra("y", coordY);

                    startActivity(i);
                    Estado = false;
                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Coordenada incorrecta",Toast.LENGTH_LONG).show();
                }

                //}

            }
        });
    }
}
