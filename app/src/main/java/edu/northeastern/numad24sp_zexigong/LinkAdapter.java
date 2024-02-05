package edu.northeastern.numad24sp_zexigong;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {

    private List<Link> linkList;
    private static OnLinkClickListener onLinkClickListener;

    public LinkAdapter(List<Link> linkList) {
        this.linkList = linkList;
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

