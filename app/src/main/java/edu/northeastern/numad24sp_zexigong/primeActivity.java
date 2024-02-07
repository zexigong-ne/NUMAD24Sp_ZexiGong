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
//        findPrimesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startPrimeSearch();
//            }
//        });

        Button terminateSearchButton = findViewById(R.id.terminateSearchButton);
        terminateSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminatePrimeSearch();
            }
        });

        // Other functionalities as required
    }
    public void runOnRunnableThread(View view) {
        RunnableThread runnableThread = new RunnableThread();
        new Thread(runnableThread).start();
    }

    class RunnableThread implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                final int finalI = i;
                //The handler changes the TextView running in the UI thread.
                textHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        statusText.setText("New thread (Runnable interface): " + finalI);
                        if (finalI == 10) {
                            statusText.setText("");
                        }
                    }
                });
                try {
                    Thread.sleep(1000); //Makes the thread sleep or be inactive for 10 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//    private void startPrimeSearch() {
//        if (!isSearching) {
//            isSearching = true;
//            primeSearchThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // Prime number search logic
//                    int currentNumber = 3;
//                    while (isSearching) {
//                        if (isPrime(currentNumber)) {
//                            int finalCurrentNumber = currentNumber;
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    updateLatestPrime(finalCurrentNumber);
//                                }
//                            });
//                        }
//                        int finalCurrentNumber1 = currentNumber;
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                updateCurrentNumber(finalCurrentNumber1);
//                            }
//                        });
//                        currentNumber += 2; // Increment by two
//                    }
//                }
//            });
//            primeSearchThread.start();
//        }
//    }

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

    private void updateCurrentNumber(final int number) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentNumberTextView.setText("Current Number: " + number);
            }
        });
    }

//    private void postUpdateToUI(final int prime) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                updateLatestPrime(prime);
//            }
//        });
//    }

    private void updateLatestPrime(final int prime) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                latestPrimeTextView.setText("Latest Prime: " + prime);
            }
        });
    }

    private void terminatePrimeSearch() {
        isSearching = false;
        if (primeSearchThread != null) {
            try {
                primeSearchThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            primeSearchThread = null;
        }
    }

}
