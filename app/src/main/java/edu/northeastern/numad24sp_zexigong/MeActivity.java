package edu.northeastern.numad24sp_zexigong;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class MeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // Get TextViews from the layout
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);

        // Set your name and email
        nameTextView.setText("Zexi Gong");
        emailTextView.setText("gong.zex@northeastern.edu");
    }
}
