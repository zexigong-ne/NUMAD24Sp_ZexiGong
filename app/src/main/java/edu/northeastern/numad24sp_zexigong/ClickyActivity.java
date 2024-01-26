package edu.northeastern.numad24sp_zexigong;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClickyActivity extends AppCompatActivity {
    // ClickyActivity.java

    private TextView pressedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);

        pressedText = findViewById(R.id.pressedText);

        // Buttons
        Button buttonA = findViewById(R.id.buttonA);
        Button buttonB = findViewById(R.id.buttonB);
        Button buttonC = findViewById(R.id.buttonC);
        Button buttonD = findViewById(R.id.buttonD);
        Button buttonE = findViewById(R.id.buttonE);
        Button buttonF = findViewById(R.id.buttonF);

        // Set click listeners
        setClickListener(buttonA, "A");
        setClickListener(buttonB, "B");
        setClickListener(buttonC, "C");
        setClickListener(buttonD, "D");
        setClickListener(buttonE, "E");
        setClickListener(buttonF, "F");
    }

    private void setClickListener(Button button, final String buttonText) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update pressed text
                pressedText.setText("Pressed: " + buttonText);
            }
        });
    }
    }

