package com.diam.applicationenregistreuraudio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class AudioRecyclerAdapter extends RecyclerView.Adapter<AudioRecyclerAdapter.AudioViewHolder> {
    private File[] audioList;
    private TimeAgo timeAgo;
    private onItemListClick listClick;

    public AudioRecyclerAdapter(File[] audioList, onItemListClick listClick) {
        this.audioList = audioList;
        this.listClick = listClick;
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_card_view, parent, false);
        timeAgo = new TimeAgo();
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        holder.titleText.setText(audioList[position].getName());
        holder.dateText.setText(timeAgo.getTimeAgo(audioList[position].lastModified()));
    }

    @Override
    public int getItemCount() {
        return audioList.length;
    }

    public class AudioViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleText, dateText;
        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.audio_image_view);
            titleText = itemView.findViewById(R.id.audio_title);
            dateText = itemView.findViewById(R.id.audio_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listClick.onItemClickListener(audioList[getAdapterPosition()], getAdapterPosition());
                }
            });
        }
    }


    public interface onItemListClick{
        void onItemClickListener(File file, int position);
    }
}
