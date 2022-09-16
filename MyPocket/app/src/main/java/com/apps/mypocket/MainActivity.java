package com.apps.mypocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {

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
                .fragment(R.layout.fragment_slide_5)
                .canGoForward(false)
                .build()
        );
    }
}