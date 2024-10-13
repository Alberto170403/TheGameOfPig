package com.example.thegameofpig;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView player1ScoreText, player2ScoreText, currentScoreText;
    private ImageView diceImage;
    private Button rollDiceButton, holdButton, resetButton;
    private View viewPlayer1, viewPlayer2;

    private int player1Score = 0;
    private int player2Score = 0;
    private int currentScore = 0;
    private int activePlayer = 1;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1ScoreText = findViewById(R.id.player1_score);
        player2ScoreText = findViewById(R.id.player2_score);
        currentScoreText = findViewById(R.id.current_score);
        diceImage = findViewById(R.id.dice_image);
        rollDiceButton = findViewById(R.id.roll_dice_button);
        holdButton = findViewById(R.id.hold_button);
        resetButton = findViewById(R.id.reset_button);
        viewPlayer1 = findViewById(R.id.viewPlayer1);
        viewPlayer2 = findViewById(R.id.viewPlayer2);

        random = new Random();

        displayInitialDiceImage();

        rollDiceButton.setOnClickListener(v -> rollDice());

        holdButton.setOnClickListener(v -> hold());

        resetButton.setOnClickListener(v -> resetGame());

        updatePlayerView();
    }

    private void displayInitialDiceImage() {
        diceImage.setImageResource(R.mipmap.dice_random);
    }

    private void resetGame() {
        player1Score = 0;
        player2Score = 0;
        currentScore = 0;
        activePlayer = 1;

        player1ScoreText.setText("Player 1 Score: " + player1Score);
        player2ScoreText.setText("Player 2 Score: " + player2Score);
        currentScoreText.setText("Current Score: " + currentScore);

        displayInitialDiceImage();
        updatePlayerView();
        Toast.makeText(this, "Game reset", Toast.LENGTH_SHORT).show();
    }

    private void rollDice() {
        int diceValue = random.nextInt(6) + 1;
        updateDiceImage(diceValue);

        if (diceValue == 1) {
            currentScore = 0;
            Toast.makeText(this, "Player " + activePlayer + " rolled a 1! Turn lost.", Toast.LENGTH_SHORT).show();
            switchPlayer();
        } else {
            currentScore += diceValue;
            currentScoreText.setText("Current Score: " + currentScore);
        }
    }

    private void updateDiceImage(int diceValue) {
        String imageName = "dice_" + convertNumberToWord(diceValue);
        int resId = getResources().getIdentifier(imageName, "mipmap", getPackageName());
        diceImage.setImageResource(resId);
    }

    private String convertNumberToWord(int number) {
        switch (number) {
            case 1:
                return "one";
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
            case 5:
                return "five";
            case 6:
                return "six";
            default:
                return "one";
        }
    }

    private void hold() {
        if (activePlayer == 1) {
            player1Score += currentScore;
            player1ScoreText.setText("Player 1 Score: " + player1Score);
            if (player1Score >= 100) {
                Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
                resetGame();
            } else {
                switchPlayer();
            }
        } else {
            player2Score += currentScore;
            player2ScoreText.setText("Player 2 Score: " + player2Score);
            if (player2Score >= 100) {
                Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
                resetGame();
            } else {
                switchPlayer();
            }
        }
    }


    private void switchPlayer() {
        currentScore = 0;
        currentScoreText.setText("Current Score: " + currentScore);
        activePlayer = (activePlayer == 1) ? 2 : 1;
        updatePlayerView();
        Toast.makeText(this, "Now Player " + activePlayer + "'s turn", Toast.LENGTH_SHORT).show();
    }

    private void updatePlayerView() {
        if (activePlayer == 1) {
            viewPlayer1.setBackgroundColor(ContextCompat.getColor(this, R.color.active_player_color));
            viewPlayer2.setBackgroundColor(ContextCompat.getColor(this, R.color.inactive_player_color));
        } else {
            viewPlayer1.setBackgroundColor(ContextCompat.getColor(this, R.color.inactive_player_color));
            viewPlayer2.setBackgroundColor(ContextCompat.getColor(this, R.color.active_player_color));
        }
    }
}
