package edu.northeastern.numad24sp_zexigong;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import 	android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Button meButton;
    private Button clickyButton;
    private Button linkCollectorButton;
    private Button primeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.meButton = (Button)this.findViewById(R.id.me);
        this.clickyButton = (Button)this.findViewById(R.id.clickyButton);
        this.linkCollectorButton = (Button)this.findViewById(R.id.linkCollectorButton);
        this.primeButton = (Button)this.findViewById(R.id.primeButton);
        Context parent = this;
        this.meButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start new activity when the button is clicked
                Intent intent = new Intent(MainActivity.this, MeActivity.class);
                startActivity(intent);
            }
        });
        this.clickyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start new activity when the button is clicked
                Intent intent = new Intent(MainActivity.this, ClickyActivity.class);
                startActivity(intent);
            }
        });
        this.linkCollectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LinkCollectorActivity.class);
                startActivity(intent);
            }
        });
        this.primeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, primeActivity.class);
                startActivity(intent);
            }
        });
    }
}
