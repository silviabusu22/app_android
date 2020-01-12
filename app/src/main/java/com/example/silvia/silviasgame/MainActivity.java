package com.example.silvia.silviasgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {


    private TextView tvUserScore ,tvComputerScore,tvStatus;
    private ImageView imDiceFace;
    private Button bRoll,bReset;
    private int[] diceFaceImages={
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6
    };
    private int userOverallScore=0, userTurnScore=0;
    private int computerOverallScore=0, computerTurnScore=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkAllViews();
        bRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollButtonClick();
            }
        });


        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetButtonClick();
            }
        });


    }

    public void linkAllViews() {
        tvUserScore = findViewById(R.id.user_score);
        tvComputerScore = findViewById(R.id.comp_score);
        tvStatus = findViewById(R.id.game_status);
        imDiceFace = findViewById(R.id.img_face);
        bRoll = findViewById((R.id.roll_button));
        bReset = findViewById(R.id.reset_button);
    }

    public void rollButtonClick() {
        int rolledNumber = rollDice();
        imDiceFace.setImageResource(diceFaceImages[rolledNumber]);
        rolledNumber++;

        if(rolledNumber==1) {
            userTurnScore=0;
            tvStatus.setText("You rolled a 1 :(");
            computerTurn();
        }
        else {
            userTurnScore += rolledNumber;
            tvStatus.setText("Your turn score is " + userTurnScore);
        }
    }



    public void holdButtonClick() {
        userOverallScore += userTurnScore;
        userTurnScore=0;
        tvUserScore.setText("Your Score: " + userOverallScore);
        if(!checkWinner()) {
            computerTurn();
        }

    }

    public void resetButtonClick() {
        userOverallScore = 0;
        userTurnScore = 0;
        computerOverallScore = 0;
        computerTurnScore = 0;
        tvUserScore.setText("Your Score: " + userOverallScore);
        tvComputerScore.setText("Computer Score: 0");
        tvStatus.setText("Status: Game Start");

        Log.d("Reset","yes it was reset");

        enableButton(true);
    }

    public boolean checkWinner() {
        if (userOverallScore >=100) {
            Toast.makeText(getApplicationContext(),"You Won :)",Toast.LENGTH_SHORT).show();
            resetButtonClick();
            return true;
        }
        else if (computerOverallScore >=100) {
            Toast.makeText(getApplicationContext(),"Computer Won :(",Toast.LENGTH_SHORT).show();
            resetButtonClick();
            return true;
        }
        return false;
    }

    private void enableButton(boolean isEnabled) {
        bRoll.setEnabled(isEnabled);
    }

    private void computerTurn() {
        enableButton(false);
        while(true) {
            int computerRolldNumber = rollDice();
            imDiceFace.setImageResource(diceFaceImages[computerRolldNumber]);
            computerRolldNumber++;
            if(computerRolldNumber == 1) {
                computerTurnScore = 0;
                tvStatus.setText("Computer rolled a 1");
                tvStatus.setText("Now player's turn");
                enableButton(true);
                break;
            }
            else {
                computerTurnScore += computerRolldNumber;
                tvStatus.setText("Computer turn score is " + computerTurnScore);
            }
            if (computerTurnScore > 20) {
                computerOverallScore += computerTurnScore;
                computerTurnScore = 0;
                tvComputerScore.setText("Computer Score: " + computerOverallScore);
                tvStatus.setText("Now player's turn");
                enableButton(true);
                break;
            }

        }
        checkWinner();
    }

    private int rollDice() {
        Random random = new Random();
        return random.nextInt(6);
    }
}
