package com.apps.fabmenu;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    private Boolean clicked = false;

    private FloatingActionButton btnFabAdd;
    private FloatingActionButton btnFabEdit;
    private FloatingActionButton btnFabImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        btnFabAdd = findViewById(R.id.fabAdd);
        btnFabEdit = findViewById(R.id.fabEdit);
        btnFabImage = findViewById(R.id.fabImage);

        btnFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnAddClick();
            }
        });
        btnFabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Edit Activity",
                        Toast.LENGTH_LONG).show();
            }
        });
        btnFabImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Image Activity",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onBtnAddClick() {
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        clicked = !clicked;
    }

    private void setVisibility(Boolean clicked) {
        if (!clicked) {
            btnFabEdit.setVisibility(View.VISIBLE);
            btnFabImage.setVisibility(View.VISIBLE);
        } else {
            btnFabEdit.setVisibility(View.INVISIBLE);
            btnFabImage.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(Boolean clicked) {
        if (!clicked) {
            btnFabEdit.startAnimation(fromBottom);
            btnFabImage.startAnimation(fromBottom);
            btnFabAdd.startAnimation(rotateOpen);

        } else {
            btnFabEdit.startAnimation(toBottom);
            btnFabImage.startAnimation(toBottom);
            btnFabAdd.startAnimation(rotateClose);
        }
    }

    private void setClickable(Boolean clicked){
        if(!clicked){
            btnFabEdit.setClickable(true);
            btnFabImage.setClickable(true);
        }else {
            btnFabEdit.setClickable(false);
            btnFabImage.setClickable(false);
        }
    }

}