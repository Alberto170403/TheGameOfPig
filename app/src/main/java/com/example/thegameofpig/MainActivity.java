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
    private int activePlayer = 1; // 1 para el jugador 1, 2 para el jugador 2
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
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

        // Tirar el dado
        rollDiceButton.setOnClickListener(v -> rollDice());

        // Mantener puntaje
        holdButton.setOnClickListener(v -> hold());

        // Reiniciar juego
        resetButton.setOnClickListener(v -> resetGame());

        // Mostrar jugador activo
        updatePlayerView();
    }

    // Reiniciar juego
    private void resetGame() {
        player1Score = 0;
        player2Score = 0;
        currentScore = 0;
        activePlayer = 1;

        player1ScoreText.setText("Player 1 Score: " + player1Score);
        player2ScoreText.setText("Player 2 Score: " + player2Score);

        diceNumberText.setText("1"); // Restablecer el número del dado a 1
        updatePlayerView(); // Actualiza la vista de los jugadores
        Toast.makeText(this, "Game reset", Toast.LENGTH_SHORT).show();
    }

    // Tirar el dado
    private void rollDice() {
        int diceValue = random.nextInt(6) + 1; // Valor entre 1 y 6
        diceNumberText.setText(String.valueOf(diceValue)); // Mostrar el valor en el TextView

        if (diceValue == 1) {
            currentScore = 0;
            Toast.makeText(this, "Player " + activePlayer + " rolled a 1! Turn lost.", Toast.LENGTH_SHORT).show();
            switchPlayer();
        } else {
            currentScore += diceValue;
        }
    }

    // Mantener puntaje y cambiar turno
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

    // Cambiar de jugador
    private void switchPlayer() {
        currentScore = 0;
        activePlayer = (activePlayer == 1) ? 2 : 1;
        updatePlayerView();
        Toast.makeText(this, "Now Player " + activePlayer + "'s turn", Toast.LENGTH_SHORT).show();
    }

    // Actualizar la vista del jugador activo
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