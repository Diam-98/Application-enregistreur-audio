package com.diam.applicationenregistreuraudio;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class RecorderFragment extends Fragment {

    private NavController navController;
    private ImageView listButton;
    private ImageView recordButton;
    private boolean isRecording = false;
    private String requestPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;
    private MediaRecorder mediaRecorder;
    private String recordFile;
    private Chronometer timer;
    TextView textIndication;

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
        timer = view.findViewById(R.id.chronometer);
        textIndication = view.findViewById(R.id.record_file_name);

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
                    stopRecording();
                    recordButton.setImageDrawable(getResources().getDrawable(R.drawable.play_button_icon_png, null));
                    isRecording = false;
                    textIndication.setText("Appuyer sur le micro pour enregistrer");
                }else{
                    if (checkPermission()){
                        startRecording();
                        recordButton.setImageDrawable(getResources().getDrawable(R.drawable.stop_button_icon, null));
                        isRecording = true;
                        textIndication.setText("L'enregistrement a commence : appuyer sur le bouton pour arreter");
                    }else{

                    }

                }
            }
        });
    }

    private void startRecording() {
        timer.start();
        String recorderPath = getActivity().getExternalFilesDir("/").getAbsolutePath();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.CANADA);
        Date now = new Date();
        recordFile = "recording_"+formatter.format(now)+".3gp";
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recorderPath + "/" + recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    private void stopRecording() {
        timer.setBase(SystemClock.elapsedRealtime());
        timer.stop();
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private boolean checkPermission(){
        if (ActivityCompat.checkSelfPermission(getContext(), requestPermission)
                == PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{requestPermission}, PERMISSION_CODE);
            return false;
        }

    }
}