package com.gordonseto.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView sumTextView;
    TextView resultTextView;
    TextView pointsTextView;
    TextView timerTextView;
    Button playAgainButton;
    RelativeLayout gameLayout;

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    final int NUM_ANSWERS = 4;

    int score = 0;
    int numberOfQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameLayout = (RelativeLayout)findViewById(R.id.gameLayout);

        startButton = (Button)findViewById(R.id.startButton);
        sumTextView = (TextView)findViewById(R.id.sumTextView);
        resultTextView = (TextView)findViewById(R.id.resultTextView);
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        playAgainButton = (Button)findViewById(R.id.playAgainButton);

        button0 = (Button)findViewById(R.id.button);
        button1 = (Button)findViewById(R.id.button2);
        button2 = (Button)findViewById(R.id.button3);
        button3 = (Button)findViewById(R.id.button4);

    }

    public void onGoPressed(View view){
        startGame();
        startButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
    }

    public void startGame(){
        score = 0;
        numberOfQuestions = 0;

        resultTextView.setText("");
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        playAgainButton.setVisibility(View.INVISIBLE);

        enableButtons();

        new CountDownTimer(10100, 1000) {

            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l/1000) + "s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                resultTextView.setText("Your score: " + getScore());
                playAgainButton.setVisibility(View.VISIBLE);
                disableButtons();
            }
        }.start();

        generateQuestion();
    }

    public void enableButtons(){
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
    }

    public void disableButtons(){
        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
    }

    public void updateLabel(int a, int b) {
        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));
    }

    public void generateQuestion(){
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        updateLabel(a, b);

        int incorrectAnswer;
        locationOfCorrectAnswer = rand.nextInt(NUM_ANSWERS);
        answers.clear();
        for (int i = 0; i < NUM_ANSWERS; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(a + b);
            } else {
                do {
                    incorrectAnswer = rand.nextInt(41);
                } while (incorrectAnswer == (a + b));
                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void chooseAnswer(View view){
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            Log.i("correct", "Correct");
            score++;
            resultTextView.setText("Correct!");
        } else {
            resultTextView.setText("Wrong!");
        }
        numberOfQuestions++;
        pointsTextView.setText(getScore());
        generateQuestion();
    }

    public void onPlayAgainPressed(View view){
        startGame();
    }

    public String getScore(){
        return Integer.toString(score) + "/" + Integer.toString(numberOfQuestions);
    }
}
