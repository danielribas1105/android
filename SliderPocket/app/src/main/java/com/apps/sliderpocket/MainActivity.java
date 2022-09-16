package com.apps.sliderpocket;

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

        setButtonBackVisible(false);
        setButtonNextVisible(false);


        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.fragment_slider_1)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.fragment_slider_2)
                .build()
        );


        /*
        addSlide(new SimpleSlide.Builder()
                .title("Título 1")
                .description("Conteúdo slide 1")
                .image(R.drawable.um)
                .background(R.color.white)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Título 2")
                .description("Conteúdo slide 2")
                .image(R.drawable.dois)
                .background(R.color.white)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Título 3")
                .description("Conteúdo slide 3")
                .image(R.drawable.tres)
                .background(R.color.white)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Título 4")
                .description("Conteúdo slide 4")
                .image(R.drawable.quatro)
                .background(R.color.white)
                .build());

         */
    }
}