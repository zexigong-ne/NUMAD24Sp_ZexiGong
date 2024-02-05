package edu.northeastern.numad24sp_zexigong;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity {

    private List<Link> linkList = new ArrayList<>();
    private LinkAdapter linkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        RecyclerView recyclerView = findViewById(R.id.linkRecyclerView);
        linkAdapter = new LinkAdapter(linkList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(linkAdapter);

        FloatingActionButton fabAddLink = findViewById(R.id.fabAddLink);
        fabAddLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLinkEntryDialog();
            }
        });
        // Create and set the adapter
        linkAdapter = new LinkAdapter(linkList);
        recyclerView.setAdapter(linkAdapter);

        // Set the click listener for URL clicks
        linkAdapter.setOnLinkClickListener(new LinkAdapter.OnLinkClickListener() {
            @Override
            public void onLinkClick(String url) {
                openUrlInBrowser(url);
            }
        });
    }

    // Add a method to open the URL in a web browser
    private void openUrlInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void showLinkEntryDialog() {
        // Inflate the dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_link_entry, null);

        // Find dialog views
        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final EditText editTextUrl = dialogView.findViewById(R.id.editTextUrl);
        Button buttonAddLink = dialogView.findViewById(R.id.buttonAddLink);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Add Link");

        // Set up the button click handler
        final AlertDialog dialog = builder.create();
        buttonAddLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String url = editTextUrl.getText().toString();

                if (!name.isEmpty() && !url.isEmpty()) {
                    // Add the link to the list
                    linkList.add(new Link(name, url));
                    linkAdapter.setLinks(linkList);

                    // Show a Snackbar
                    Snackbar.make(findViewById(android.R.id.content), "Link added successfully", Snackbar.LENGTH_SHORT)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Implement undo action if needed
                                    linkList.remove(linkList.size() - 1);
                                    linkAdapter.setLinks(linkList);
                                }
                            }).show();

                    // Dismiss the dialog
                    dialog.dismiss();
                } else {
                    // Display an error message if fields are empty
                    Snackbar.make(findViewById(android.R.id.content), "Please enter both name and URL", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        // Show the dialog
        dialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("linkList", new ArrayList<>(linkList));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            linkList = savedInstanceState.getParcelableArrayList("linkList");
            linkAdapter.setLinks(linkList);
        }
    }
}
