package com.apps.aprendaingles.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.apps.aprendaingles.R;


public class AnimaisFragment extends Fragment implements View.OnClickListener {

    private ImageButton btnCao, btnGato, btnLeao, btnMacaco, btnOvelha, btnVaca;
    private MediaPlayer mediaPlayer;

    public AnimaisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animais, container, false);

        btnCao = view.findViewById(R.id.btnCao);
        btnGato = view.findViewById(R.id.btnGato);
        btnLeao = view.findViewById(R.id.btnLeao);
        btnMacaco = view.findViewById(R.id.btnMacaco);
        btnOvelha = view.findViewById(R.id.btnOvelha);
        btnVaca = view.findViewById(R.id.btnVaca);

        btnCao.setOnClickListener(this);
        btnGato.setOnClickListener(this);
        btnLeao.setOnClickListener(this);
        btnMacaco.setOnClickListener(this);
        btnOvelha.setOnClickListener(this);
        btnVaca.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        //Log.i("Elemento clicado: ","Item "+view.getId());
        switch (view.getId()) {
            case R.id.btnCao:
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.dog);
                tocarSom();
                break;
            case R.id.btnGato:
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.cat);
                tocarSom();
                break;
            case R.id.btnLeao:
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.lion);
                tocarSom();
                break;
            case R.id.btnMacaco:
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.monkey);
                tocarSom();
                break;
            case R.id.btnOvelha:
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.sheep);
                tocarSom();
                break;
            case R.id.btnVaca:
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.cow);
                tocarSom();
                break;
        }

    }

    public void tocarSom() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}