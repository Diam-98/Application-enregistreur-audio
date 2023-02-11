package com.diam.applicationenregistreuraudio;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class RecorderFragment extends Fragment {

    private NavController navController;
    private ImageView listButton;
    private ImageView recordButton;
    private boolean isRecording = false;

    public RecorderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recorder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        listButton = view.findViewById(R.id.record_list_icon);
        recordButton = view.findViewById(R.id.record_btn);

        listButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_recorderFragment_to_audioListFragment);
            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (isRecording){
                    recordButton.setImageDrawable(getResources().getDrawable(R.drawable.black_music_note_icon, null));
                    isRecording = false;
                }else{
                    recordButton.setImageDrawable(getResources().getDrawable(R.drawable.record_logo, null));
                    isRecording = true;
                }
            }
        });
    }
}