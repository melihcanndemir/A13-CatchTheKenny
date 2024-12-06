package com.melihcandemir.catchthekenny;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView timeText, scoreText;
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    ImageView[] imageArray;
    CountDownTimer imageShuffleTimer, gameTimeTimer;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);

        imageArray = new ImageView[]{imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};

        timeText = findViewById(R.id.timeText);
        scoreText = findViewById(R.id.scoreText);
        score = 0;
        gameTime();
        startImageShuffle();
    }

    @SuppressLint("SetTextI18n")
    public void gameTime() {
        score = 0;
        scoreText.setText("Score: " + score);

        gameTimeTimer = new CountDownTimer(10000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: " + millisUntilFinished / 1000);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                gameOver();
                timeText.setText("Time's Off");
            }
        }.start();
    }

    @SuppressLint("SetTextI18n")
    public void increaseScore(View view) {
        score++;
        scoreText.setText("Score: " + score);
    }

    public void hideImages() {
        for (ImageView image : imageArray) {
            image.setVisibility(View.INVISIBLE);
        }
        Random random = new Random();
        int i = random.nextInt(9);
        imageArray[i].setVisibility(View.VISIBLE);
    }

    public void enableKenny() {
        for (ImageView image : imageArray) {
            image.setEnabled(true);
        }
    }

    public void disableKenny() {
        for (ImageView image : imageArray) {
            image.setEnabled(false);
        }
    }

    public void startImageShuffle() {
        imageShuffleTimer = new CountDownTimer(10000, 350) {

            @Override
            public void onTick(long millisUntilFinished) {
                hideImages();
            }

            @Override
            public void onFinish() {
                hideImages();
            }
        }.start();
    }

    public void gameOver() {
        if (imageShuffleTimer != null) {
            imageShuffleTimer.cancel();
        }
        if (gameTimeTimer != null) {
            gameTimeTimer.cancel();
        }

        hideImages();
        for (ImageView image : imageArray) {
            image.setVisibility(View.INVISIBLE);
        }
        disableKenny();

        AlertDialog.Builder restartGame = new AlertDialog.Builder(this);
        restartGame.setTitle("Restart?");
        restartGame.setMessage("Are you restart the game?");
        restartGame.setPositiveButton("Yes", (dialog, which) -> {
            enableKenny();
            gameTime();
            startImageShuffle();
        });

        restartGame.setNegativeButton("No", (dialog, which) ->
            Toast.makeText(MainActivity.this, "Game Over", Toast.LENGTH_SHORT).show());
        restartGame.show();
    }
}