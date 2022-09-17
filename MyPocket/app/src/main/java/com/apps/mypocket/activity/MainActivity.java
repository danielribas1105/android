package com.apps.mypocket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.mypocket.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        setButtonNextVisible(false);
        setButtonNextVisible(false);


        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.fragment_slide_1)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.fragment_slide_2)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.fragment_slide_3)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.fragment_slide_4)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.fragment_slide_cadastro)
                .canGoForward(false)
                .build()
        );
    }

    public void btnCadastrar (View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void btnLogin(View view){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

}