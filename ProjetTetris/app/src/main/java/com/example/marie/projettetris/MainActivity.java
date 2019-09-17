package com.example.marie.projettetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button ButtonHard;
    Button ButtonMedium;
    Button ButtonEasy;
    Button ButtonOriginal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButtonOriginal = findViewById(R.id.buttonOriginal);
        ButtonHard = findViewById(R.id.buttonHard);
        ButtonMedium = findViewById(R.id.buttonMedium);
        ButtonEasy = findViewById(R.id.buttonEasy);
        ButtonOriginal.setOnClickListener(this);
        ButtonHard.setOnClickListener(this);
        ButtonMedium.setOnClickListener(this);
        ButtonEasy.setOnClickListener(this);
        int score = 0;
        Boolean finJeu = false;
        Boolean Gagnant = false;

        score = getIntent().getIntExtra("score",0);
        Gagnant = getIntent().getBooleanExtra("gagnant",false);
        finJeu = getIntent().getBooleanExtra("finJeu",false);


        if(finJeu){
            if(Gagnant){
                Toast.makeText(MainActivity.this,"Winner !! tu as un score de : "+score,Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(MainActivity.this,"Looser !! tu as un score de : "+score,Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onClick(View v) {
        String difficulte = "";
        difficulte = String.valueOf(v.getTag());

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulte",difficulte);

        startActivity(intent);
    }



}
