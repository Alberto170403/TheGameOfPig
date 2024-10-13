package com.example.thegameofpig;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private TextView player1ScoreText, player2ScoreText, currentScoreText, diceNumberText;
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
        diceNumberText = findViewById(R.id.dice_number);
        rollDiceButton = findViewById(R.id.roll_dice_button);
        holdButton = findViewById(R.id.hold_button);
        resetButton = findViewById(R.id.reset_button);
        viewPlayer1 = findViewById(R.id.viewPlayer1);
        viewPlayer2 = findViewById(R.id.viewPlayer2);

        random = new Random();

        rollDiceButton.setOnClickListener(v -> rollDice());

        holdButton.setOnClickListener(v -> hold());

        resetButton.setOnClickListener(v -> resetGame());

        updatePlayerView();
    }

    private void resetGame() {
        player1Score = 0;
        player2Score = 0;
        currentScore = 0;
        activePlayer = 1;

        player1ScoreText.setText("Player 1 Score: " + player1Score);
        player2ScoreText.setText("Player 2 Score: " + player2Score);

        diceNumberText.setText("1");
        updatePlayerView();
        Toast.makeText(this, "Game reset", Toast.LENGTH_SHORT).show();
    }

    private void rollDice() {
        int diceValue = random.nextInt(6) + 1;
        diceNumberText.setText(String.valueOf(diceValue));

        if (diceValue == 1) {
            currentScore = 0;
            Toast.makeText(this, "Player " + activePlayer + " rolled a 1! Turn lost.", Toast.LENGTH_SHORT).show();
            switchPlayer();
        } else {
            currentScore += diceValue;
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