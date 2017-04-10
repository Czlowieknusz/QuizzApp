package com.example.android.quizzapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*
        going to random class onClick
     */
    public void loadGameScreen(View view) {
        Random rand = new Random();
        int n = rand.nextInt(3);
        if (n == 0) {
            Intent i = new Intent(this, checkBoxWindow.class);
            i.putExtra("numberOfPoints", "0");
            i.putExtra("numberOfRoundsPlayed", "1");
            startActivity(i);
        } else if (n == 1) {
            Intent i = new Intent(this, radioButtonWindow.class);
            i.putExtra("numberOfPoints", "0");
            i.putExtra("numberOfRoundsPlayed", "1");
            startActivity(i);
        } else {
            Intent i = new Intent(this, textAnswerWindow.class);
            i.putExtra("numberOfPoints", "0");
            i.putExtra("numberOfRoundsPlayed", "1");
            startActivity(i);
        }
    }
    private void startingNewWindow() {
    }
}
