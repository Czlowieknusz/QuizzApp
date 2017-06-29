package com.example.android.quizzapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static com.example.android.quizzapp.R.id.checkBox;

public class checkBoxWindow extends AppCompatActivity {
    private String[] questionsArray;
    private CheckBox answerBox1, answerBox2, answerBox3, answerBox4;
    private boolean submitted = false;
    int score, roundsPlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_box_window);
        /*
        downloading score value and number of rounds from extra Intent
        checking if extraIntent was passed correctly
        */
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                score = Integer.parseInt(extras.getString("numberOfPoints"));
                roundsPlayed = Integer.parseInt(extras.getString("numberOfRoundsPlayed"));
            }
        }
        /*
        printing random question and answers on the screen
         */
        choosingQuestion();
        printingStringsOnScreen();

    }

    /*
        saving variables in case of rotating the phone
     */
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("Score", score);
        savedInstanceState.putInt("roundsPlayed", roundsPlayed);
        savedInstanceState.putStringArray("questionsArray", questionsArray);
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        questionsArray = savedInstanceState.getStringArray("questionsArray");
        printingStringsOnScreen();
        score = savedInstanceState.getInt("Score");
        roundsPlayed = savedInstanceState.getInt("roundsPlayed");
    }

    /*
    method setting text for checkBoxes and question
     */
    private void printingStringsOnScreen() {
        TextView questionText = (TextView) findViewById(R.id.questionCheckBoxWindow);
        answerBox1 = (CheckBox) findViewById(checkBox);
        answerBox2 = (CheckBox) findViewById(R.id.checkBox2);
        answerBox3 = (CheckBox) findViewById(R.id.checkBox3);
        answerBox4 = (CheckBox) findViewById(R.id.checkBox4);
        questionText.setText(questionsArray[0] + getString(R.string.adnotationAboutMultipleAnswers));
        answerBox1.setText(questionsArray[3]);
        answerBox2.setText(questionsArray[4]);
        answerBox3.setText(questionsArray[5]);
        answerBox4.setText(questionsArray[6]);
    }

    /*
        function choosing random question
        and storing it in chosenQuestion
     */
    private void choosingQuestion() {
        Random rand = new Random();
        int n = rand.nextInt(4);
        if (n == 0) {
            questionsArray = getResources().getStringArray(R.array.checkButtonQuestion1);
        } else if (n == 1) {
            questionsArray = getResources().getStringArray(R.array.checkButtonQuestion2);
        } else if (n == 2) {
            questionsArray = getResources().getStringArray(R.array.checkButtonQuestion3);
        } else if (n == 3) {
            questionsArray = getResources().getStringArray(R.array.checkButtonQuestion4);
        }
    }

    /*
        function called on Submit button clicked
        checks if answer is correct and if it was clicked before and adding point
        then if @roundsPlayed != 3 random class is started
        if @roundsPlayed == 3 game goes back to MainActivity.class
     */
    public void Submit(View view) {
        String answerBoxText1 = answerBox1.getText().toString(),
                answerBoxText2 = answerBox2.getText().toString(),
                answerBoxText3 = answerBox3.getText().toString(),
                answerBoxText4 = answerBox4.getText().toString();
        int numberOfCorrectNumbers = 0;
        int numberOfChoosenAnswers = 0;
        Context context = getApplicationContext();
        if (answerBox1.isChecked()) {
            numberOfChoosenAnswers++;
            if (answerBoxText1.equals(questionsArray[1]) || answerBoxText1.equals(questionsArray[2]))
                numberOfCorrectNumbers++;
        }
        if (answerBox2.isChecked()) {
            numberOfChoosenAnswers++;
            if (answerBoxText2.equals(questionsArray[1]) || answerBoxText2.equals(questionsArray[2]))
                numberOfCorrectNumbers++;
        }
        if (answerBox3.isChecked()) {
            numberOfChoosenAnswers++;
            if (answerBoxText3.equals(questionsArray[1]) || answerBoxText3.equals(questionsArray[2]))
                numberOfCorrectNumbers++;
        }
        if (answerBox4.isChecked()) {
            numberOfChoosenAnswers++;
            if ((answerBoxText4.equals(questionsArray[1]) || answerBoxText4.equals(questionsArray[2])))
                numberOfCorrectNumbers++;
        }
        boolean areAnswersCorrect = numberOfCorrectNumbers == 2 && numberOfChoosenAnswers == 2;
        Log.v("checkBoxWindow", "warunek " + areAnswersCorrect);
        if (!submitted && areAnswersCorrect) {
            score++;
            submitted = true;
        }
        if (roundsPlayed == 3) {
            Log.v("checkBoxWindow", "Starting MainActivity");
            Toast toast = Toast.makeText(context, "The end!\nYour total score is " + score, Toast.LENGTH_LONG);
            toast.show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            if (areAnswersCorrect) {
                Toast toast = Toast.makeText(context, "You now have " + score + " points.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast = Toast.makeText(context, "Wrong. Correct answer is " + questionsArray[1] + " and " + questionsArray[2], Toast.LENGTH_LONG);
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