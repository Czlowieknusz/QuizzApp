package com.example.android.quizzapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class radioButtonWindow extends AppCompatActivity {
    private RadioGroup radioGroup;
    private TextView questionView;
    private String[] chosenQuestion;
    private boolean submitted = false;
    int score, roundsPlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio_button_window);
        /*
            downloading score value from extra Intent
            if it's correctly passed to this class
         */
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                score = Integer.parseInt(extras.getString("numberOfPoints"));
                roundsPlayed = Integer.parseInt(extras.getString("numberOfRoundsPlayed"));
            }
        }
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        questionView = (TextView) findViewById(R.id.question);
        choosingQuestion();
        printingStringsOnScreen();
    }

    /*
        saving variables in case of rotating the phone
     */
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("Score", score);
        savedInstanceState.putInt("roundsPlayed", roundsPlayed);
        savedInstanceState.putStringArray("chosenQuestion", chosenQuestion);
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        chosenQuestion = savedInstanceState.getStringArray("chosenQuestion");
        printingStringsOnScreen();
        score = savedInstanceState.getInt("Score");
        roundsPlayed = savedInstanceState.getInt("roundsPlayed");
    }
    /*
        function printing text for answers and question
     */
    private void printingStringsOnScreen() {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            ((RadioButton) radioGroup.getChildAt(i)).setText(String.valueOf(chosenQuestion[i + 2]));
        }
        questionView.setText(String.valueOf(chosenQuestion[0]));
    }

    /*
        function choosing random question
        and storing it in chosenQuestion
     */
    private void choosingQuestion() {
        Resources res = getResources();
        Random rand = new Random();
        int n = rand.nextInt(4);
        if (n == 0) {
            chosenQuestion = res.getStringArray(R.array.radioQuestion1);
        } else if (n == 1) {
            chosenQuestion = res.getStringArray(R.array.radioQuestion2);
        } else if (n == 2) {
            chosenQuestion = res.getStringArray(R.array.radioQuestion3);
        } else if (n == 3) {
            chosenQuestion = res.getStringArray(R.array.radioQuestion4);
        }
    }

    /*
        function called on Submit button clicked
        checks if answer is correct and if it was clicked before and adding point
        then if @roundsPlayed != 3 random class is started
        if @roundsPlayed == 3 game goes back to MainActivity.class
     */

    public void submit(View view) {
        RadioButton Answer;
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        if (radioButtonID != -1) {
            Answer = (RadioButton) findViewById(radioButtonID);
            String answer = Answer.getText().toString();
            Context context = getApplicationContext();
            if (chosenQuestion[1].equals(answer) && !submitted) {
                score++;
                submitted = true;
            }
            if (roundsPlayed == 3) {
                Toast toast = Toast.makeText(context, "The end!\nYour total score equals " + score, Toast.LENGTH_LONG);
                toast.show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else {
                if (chosenQuestion[1].equals(answer)) {
                    Toast toast = Toast.makeText(context, "You now have " + score + " points.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(context, "Wrong. Correct answer is " + chosenQuestion[1], Toast.LENGTH_LONG);
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
}