package com.apps.mypocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apps.mypocket.activity.CadastroActivity;
import com.apps.mypocket.activity.LoginActivity;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity  {

    //private Button btnCadastrar = findViewById(R.id.btnCadastrar);
    //private TextView btnLogin = findViewById(R.id.textTenhoCadastro);

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