package com.example.setearlatitudformulario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText x, y;
    Button aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = (EditText)findViewById(R.id.editTextX);
        y = (EditText)findViewById(R.id.editTextY);

        aceptar = (Button)findViewById(R.id.boton);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coordX = x.getText().toString();
                String coordY = y.getText().toString();

                try {
                    Float.parseFloat(coordX);
                    Float.parseFloat(coordY);

                    Intent i = new Intent(MainActivity.this, MapsActivity.class);
                    i.putExtra("x", coordX);
                    i.putExtra("y", coordY);

                    startActivity(i);

                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Coordenada incorrecta",Toast.LENGTH_LONG).show();
                }

                //}

            }
        });
    }
}
