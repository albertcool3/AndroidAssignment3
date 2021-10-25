package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.utilities.NetworkUtils;
import com.example.myapplication.utilities.OpenWeatherJsonUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView mWeatherTextView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PDF button
        // button = findViewById(R.id.btn);

       //Navigation
        bottomNavigationView =findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new WeatherFragment()).commit();

        //Weather
        //mWeatherTextView = (TextView) findViewById(R.id.weather_test);

        //loadWeatherData();


    }


    private void PDFTest(View view){

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(MainActivity.this, PdfActivity.class);
                startActivity(intent);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment fragment = null;
                    switch (menuItem.getItemId()) {

                        case R.id.weather:
                            fragment = new WeatherFragment();
                            break;

                        case R.id.facts:
                            fragment = new FactsFragment();
                            break;

                        case R.id.portfolio:
                            fragment = new PortfolioFragment();
                            break;

                        case R.id.resume:
                            fragment = new ResumeFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                    return true;
                }
            };



}