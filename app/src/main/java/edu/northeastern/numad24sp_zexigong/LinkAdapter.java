package edu.northeastern.numad24sp_zexigong;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {

    private List<Link> linkList;
    private static OnLinkClickListener onLinkClickListener;
    private Context context;

    public LinkAdapter(List<Link> linkList, Context context) {
        this.linkList = linkList;
        this.context = context;
    }

    public void setLinks(List<Link> links) {
        linkList = links;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link, parent, false);
        return new LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        Link link = linkList.get(position);
        holder.bind(link);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition < linkList.size()) {
                    showDeleteEditDialog(adapterPosition);
                }
                return true;
            }
        });
    }
    private void showDeleteEditDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Options")
                .setItems(new CharSequence[]{"Delete", "Edit"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which) {
                            case 0:
                                // Delete option
                                deleteLink(position);
                                break;
                            case 1:
                                // Edit option
                                showEditDialog(position);
                                break;
                        }
                    }
                });
        builder.show();
    }

    private void deleteLink(int position) {
        final Link deletedLink = linkList.get(position);
        linkList.remove(position);
        notifyDataSetChanged();
        Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Link deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Undo the deletion by re-adding the link at the original position
                        linkList.add(position, deletedLink);
                        notifyDataSetChanged();
                    }
                })
                .show();
    }

    private void showEditDialog(final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit_link, null);

        final EditText editName = dialogView.findViewById(R.id.editName);
        final EditText editUrl = dialogView.findViewById(R.id.editUrl);

        // Set current values in the edit texts
        Link link = linkList.get(position);
        editName.setText(link.getName());
        editUrl.setText(link.getUrl());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Link")
                .setView(dialogView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Save changes to the link
                        String newName = editName.getText().toString();
                        String newUrl = editUrl.getText().toString();
                        updateLink(position, newName, newUrl);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // New method to handle updating the link
    private void updateLink(int position, String newName, String newUrl) {
        Link link = linkList.get(position);
        link.setName(newName);
        link.setUrl(newUrl);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    // Interface to handle URL clicks
    public interface OnLinkClickListener {
        void onLinkClick(String url);
    }

    // Set the listener for URL clicks
    public void setOnLinkClickListener(OnLinkClickListener listener) {
        this.onLinkClickListener = listener;
    }

    static class LinkViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView urlTextView;

        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            urlTextView = itemView.findViewById(R.id.urlTextView);
        }

        public void bind(Link link) {
            nameTextView.setText(link.getName());
            urlTextView.setText(link.getUrl());
            // Set click listener for the URL TextView
            urlTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onLinkClickListener != null) {
                        onLinkClickListener.onLinkClick(link.getUrl());
                    }
                }
            });
        }

    }


}

