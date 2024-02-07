package edu.northeastern.numad24sp_zexigong;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class primeActivity extends AppCompatActivity {
    private volatile boolean isSearching = false;

    private TextView currentNumberTextView;
    private TextView latestPrimeTextView;
    private CheckBox pacifierSwitch;
    private Handler textHandler = new Handler();
    private int currentNumber = 3;
    private boolean isPacifierChecked = false;
    private boolean backPressedDuringSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_search);

        currentNumberTextView = findViewById(R.id.currentNumberTextView);
        latestPrimeTextView = findViewById(R.id.latestPrimeTextView);
        pacifierSwitch = findViewById(R.id.pacifierSwitch);

        Button findPrimesButton = findViewById(R.id.findPrimesButton);
        findPrimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPrimeSearch();
            }
        });

        Button terminateSearchButton = findViewById(R.id.terminateSearchButton);
        terminateSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminatePrimeSearch();
            }
        });
        if (savedInstanceState != null) {
            isSearching = savedInstanceState.getBoolean("isSearching", false);
            currentNumber = savedInstanceState.getInt("currentNumber", 3);
            isPacifierChecked = savedInstanceState.getBoolean("isPacifierChecked", false);
            pacifierSwitch.setChecked(isPacifierChecked);
            if (isSearching) {
                startPrimeSearch();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isSearching", isSearching);
        outState.putInt("currentNumber", currentNumber);
        outState.putBoolean("isPacifierChecked", pacifierSwitch.isChecked());
    }

    public void startPrimeSearch() {
        RunnableThread runnableThread = new RunnableThread();
        new Thread(runnableThread).start();
    }

    class RunnableThread implements Runnable {

        @Override
        public void run() {
            if (!isSearching) {
            isSearching = true;
            }
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
        currentNumber = 3;
    }

    @Override
    public void onBackPressed() {
        if (isSearching) {
            // If search is running, prompt user for confirmation before closing activity
            showConfirmationDialog();
        } else {
            // If search is not running, simply finish activity
            super.onBackPressed();
        }
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search Running");
        builder.setMessage("Are you sure you want to terminate the search?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                backPressedDuringSearch = true;
                terminatePrimeSearch();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}
