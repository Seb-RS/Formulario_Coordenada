package com.example.setearlatitudformulario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText x, y;
    double latUsuario, lonUsuario;
    Button aceptar;
    Boolean Estado = true;
    TextView mensaje1, mensaje2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = (EditText) findViewById(R.id.editTextX);
        y = (EditText) findViewById(R.id.editTextY);
        x.setEnabled(false);
        y.setEnabled(false);
        this.mensaje1 = (TextView) findViewById(R.id.mensaje1);
        this.mensaje2 = (TextView) findViewById(R.id.mensaje2);

        aceptar = (Button) findViewById(R.id.boton);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            localizarUsuario();
        }

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Estado) {
                    String coordX = x.getText().toString();
                    String coordY = y.getText().toString();
                    String coordUsuarioX = String.valueOf(latUsuario);
                    String coordUsuarioY = String.valueOf(lonUsuario);

                    try {
                        Double.parseDouble(coordX);
                        Double.parseDouble(coordY);
                        Double.parseDouble(coordUsuarioX);
                        Double.parseDouble(coordUsuarioY);


                        Intent i = new Intent(MainActivity.this, MapsActivity.class);
                        i.putExtra("x", coordX);
                        i.putExtra("y", coordY);
                        i.putExtra("xuser", coordUsuarioX);
                        i.putExtra("yuser", coordUsuarioY);

                        startActivity(i);

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Coordenada incorrecta", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });
    }

    private void localizarUsuario() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        mensaje2.setText("");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                localizarUsuario();
                return;
            }
        }
    }

    public void getLocation(Location loc)
    {
        latUsuario = loc.getLatitude();
        lonUsuario = loc.getLongitude();

        if(loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0)
        {
            Estado = false;
            x.setEnabled(true);
            y.setEnabled(true);
        }
    }

    public void setLocation(Location loc) {
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    mensaje1.setText("Localización exitosa.");
                    mensaje2.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));
                    getLocation(loc);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


