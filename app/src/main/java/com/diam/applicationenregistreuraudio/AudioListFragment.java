package com.diam.applicationenregistreuraudio;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.File;
import java.io.IOException;

public class AudioListFragment extends Fragment implements AudioRecyclerAdapter.onItemListClick {

    private ConstraintLayout player_sheet;
    private BottomSheetBehavior behavior;
    private RecyclerView recyclerView;
    private File[] allFiles;
    private AudioRecyclerAdapter adapter;
    private MediaPlayer mediaPlayer = null;
    private boolean is_playing = false;
    private File fileToPlay;

    // Bottom player sheet
    TextView title, file_name;
    ImageView player_btn;

    public AudioListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        player_sheet = view.findViewById(R.id.player_sheet);
        behavior = BottomSheetBehavior.from(player_sheet);
        recyclerView = view.findViewById(R.id.audio_list_view);
        player_btn = view.findViewById(R.id.play_btn);
        title = view.findViewById(R.id.player_header_title);
        file_name = view.findViewById(R.id.player_file_name);

        String path = getActivity().getExternalFilesDir("/").getAbsolutePath();
        File directory = new File(path);
        allFiles = directory.listFiles();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AudioRecyclerAdapter(allFiles,this);
        recyclerView.setAdapter(adapter);

        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN){
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void onItemClickListener(File file, int position) {
        if (is_playing){
            stopAudio();
            fileToPlay = file;
            playAudio(fileToPlay);
        }else{
            fileToPlay = file;
            playAudio(fileToPlay);

        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void playAudio(File file){
        mediaPlayer = new MediaPlayer();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        try {
            mediaPlayer.setDataSource(fileToPlay.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        player_btn.setImageDrawable(getActivity()
                .getResources()
                .getDrawable(android.R.drawable.ic_media_pause, null));
        file_name.setText(fileToPlay.getName());
        title.setText("En lecture");
        is_playing = true;

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopAudio();
                title.setText("Fin de l'audio");
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void stopAudio(){
        player_btn.setImageDrawable(getActivity()
                .getResources()
                .getDrawable(android.R.drawable.ic_media_play));
        title.setText("En lecture");
        is_playing = false;
    }
}