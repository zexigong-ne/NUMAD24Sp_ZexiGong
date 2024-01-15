package edu.northeastern.numad24sp_zexigong;
import android.content.Context;
import android.widget.Button;
import 	android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Button meButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.meButton = (Button)this.findViewById(R.id.me);
        Context parent = this;
        this.meButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence cs = "Zexi Gong\n gong.zex@northeastern.edu";
                Toast toast = Toast.makeText(parent /* MyActivity */, cs, Toast.LENGTH_LONG);
                toast.show();
            }


        });
    }
}