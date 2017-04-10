package com.example.android.quizzapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Jam on 27.03.2017.
 */

public class textAnswerWindow extends AppCompatActivity {
    private String[] questionArray;
    private EditText answerView;
    private TextView questionView;
    int score, roundsPlayed;
    boolean submitted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_answer_window);
        answerView = (EditText) findViewById(R.id.answerInEditText);
        /*
            downloading score value from extra Intent
         */
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                score = Integer.parseInt(extras.getString("numberOfPoints"));
                roundsPlayed = Integer.parseInt(extras.getString("numberOfRoundsPlayed"));
            }
        }
        choosingQuestion();
        printingStringsOnScreen();
    }

    /*
        saving variables in case of rotating the phone
     */
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("Score", score);
        savedInstanceState.putInt("roundsPlayed", roundsPlayed);
        savedInstanceState.putStringArray("questionArray", questionArray);
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        questionArray = savedInstanceState.getStringArray("questionArray");
        printingStringsOnScreen();
        score = savedInstanceState.getInt("Score");
        roundsPlayed = savedInstanceState.getInt("roundsPlayed");
    }

    private void printingStringsOnScreen() {
        questionView = (TextView) findViewById(R.id.questionInEditText);
        questionView.setText(String.valueOf(questionArray[0]));
    }

    /*
        function choosing random question
        and storing it in chosenQuestion
     */
    private void choosingQuestion() {
        Random rand = new Random();
        int n = rand.nextInt(4);
        if (n == 0) {
            questionArray = getResources().getStringArray(R.array.editTextQuestion1);
        } else if (n == 1) {
            questionArray = getResources().getStringArray(R.array.editTextQuestion2);
        } else if (n == 2) {
            questionArray = getResources().getStringArray(R.array.editTextQuestion3);
        } else if (n == 3) {
            questionArray = getResources().getStringArray(R.array.editTextQuestion4);
        }
    }

    /*
        function called on Submit button clicked
        checks if answer is correct and if it was clicked before and adding point
        then if @roundsPlayed != 3 random class is started
        if @roundsPlayed == 3 game goes back to MainActivity.class
     */

    public void submitEditText(View view) {
        boolean isAnswerCorrect = questionArray[1].toLowerCase().equals(answerView.getText().toString().toLowerCase());
        Context context = getApplicationContext();
        if (!submitted&&isAnswerCorrect) {
            submitted = true;
            score++;
        }
        if (roundsPlayed == 3) {
            Toast toast = Toast.makeText(context, "The end!\nYour total score is " + score, Toast.LENGTH_LONG);
            toast.show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            if (isAnswerCorrect) {
                Toast toast = Toast.makeText(context, "You now have " + score + " points.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast = Toast.makeText(context, "Wrong. Correct answer is " + questionArray[1], Toast.LENGTH_LONG);
                toast.show();
            }
            Random rand = new Random();
            int n = rand.nextInt(3);
            if (n == 0) {
                Intent i = new Intent(this, textAnswerWindow.class);
                i.putExtra("numberOfPoints", Integer.toString(score));
                i.putExtra("numberOfRoundsPlayed", Integer.toString(roundsPlayed + 1));
                startActivity(i);
            } else if (n == 1) {
                Intent i = new Intent(this, radioButtonWindow.class);
                i.putExtra("numberOfPoints", Integer.toString(score));
                i.putExtra("numberOfRoundsPlayed", Integer.toString(roundsPlayed + 1));
                startActivity(i);
            } else {
                Intent i = new Intent(this, checkBoxWindow.class);
                i.putExtra("numberOfPoints", Integer.toString(score));
                i.putExtra("numberOfRoundsPlayed", Integer.toString(roundsPlayed + 1));
                startActivity(i);
            }
        }
    }
}