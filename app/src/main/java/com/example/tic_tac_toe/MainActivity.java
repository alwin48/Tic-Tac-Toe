package com.example.tic_tac_toe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView player1;
    private TextView player2;
    private Button[][] buttons = new Button[3][3];
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private boolean player1Turn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btn" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button resetScore = findViewById(R.id.resetScore);
        resetScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        roundCount++;
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        }
        else {
            player1Turn = !player1Turn;
        }
    }

    private void draw() {
        Toast.makeText(this,"Game Ends In A Draw",Toast.LENGTH_LONG).show();
        resetBoard();
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this,"Player 1 Wins",Toast.LENGTH_LONG).show();
        updatePoints();
        resetBoard();
    }

    private void updatePoints() {
        player1.setText("Player 1: "+ player1Points);
        player2.setText("Player 2: "+ player2Points);
    }

    private void resetBoard() {
        for(int i = 0; i <3; i++)
            for(int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        roundCount = 0;
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this,"Player 2 Wins",Toast.LENGTH_LONG).show();
        updatePoints();
        resetBoard();
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j <3; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for(int i = 0; i < 3; i++) {
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        return field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[2][0].equals("");
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePoints();
        resetBoard();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
        String[][] str = new String[3][3];

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++) {
                str[i][j] = buttons[i][j].getText().toString();
            }

        outState.putStringArray("str0",str[0]);
        outState.putStringArray("str1",str[1]);
        outState.putStringArray("str2",str[2]);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");

        player1.setText("Player 1: "+ player1Points);
        player2.setText("Player 2: "+ player2Points);

        String[][] str ={savedInstanceState.getStringArray("str0")
                ,savedInstanceState.getStringArray("str1")
                ,savedInstanceState.getStringArray("str2")};

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++) {
                assert str[i] != null;
                buttons[i][j].setText(str[i][j]);
            }
    }
}