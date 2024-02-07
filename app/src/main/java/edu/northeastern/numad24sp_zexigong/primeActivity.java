package edu.northeastern.numad24sp_zexigong;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class primeActivity extends AppCompatActivity {
    private volatile boolean isSearching = false;
    private Thread primeSearchThread;

    private TextView currentNumberTextView;
    private TextView latestPrimeTextView;
    private CheckBox pacifierSwitch;
    private Handler textHandler = new Handler();
    private static TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_search);

        currentNumberTextView = findViewById(R.id.currentNumberTextView);
        latestPrimeTextView = findViewById(R.id.latestPrimeTextView);
        pacifierSwitch = findViewById(R.id.pacifierSwitch);

        Button findPrimesButton = findViewById(R.id.findPrimesButton);
        statusText = findViewById(R.id.runStatusText);
        findPrimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPrimeSearch(v);
            }
        });

        Button terminateSearchButton = findViewById(R.id.terminateSearchButton);
        terminateSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminatePrimeSearch();
            }
        });
    }
    public void startPrimeSearch(View v) {
        RunnableThread runnableThread = new RunnableThread();
        new Thread(runnableThread).start();
    }

    class RunnableThread implements Runnable {

        @Override
        public void run() {
            if (!isSearching) {
            isSearching = true;
            }
            int currentNumber = 3;
            while (isSearching) {
                //The handler changes the TextView running in the UI thread.
                int finalCurrentNumber = currentNumber;
                textHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        currentNumberTextView.setText("Current Number: " + finalCurrentNumber);
                    }
                });
                if (isPrime(currentNumber)) {
                    textHandler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            latestPrimeTextView.setText("Latest Prime: " + finalCurrentNumber);
                        }
                    });
                }
                currentNumber += 2;
                try {
                    Thread.sleep(300); //Makes the thread sleep or be inactive for 10 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isPrime(int num) {
        if (num <= 1)
            return false;
        if (num <= 3)
            return true;
        if (num % 2 == 0 || num % 3 == 0)
            return false;
        int i = 5;
        while (i * i <= num) {
            if (num % i == 0 || num % (i + 2) == 0)
                return false;
            i += 6;
        }
        return true;
    }

    private void terminatePrimeSearch() {
        isSearching = false;
    }

}
