package com.apps.drpersonal.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.apps.drpersonal.R;
import com.apps.drpersonal.ui.fragment.ImgExercFragment;
import com.apps.drpersonal.ui.fragment.DescExercFragment;
import com.apps.drpersonal.ui.fragment.VideoExercFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class InfoExercActivity extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_exerc);

        smartTabLayout = findViewById(R.id.viewPagerTab);
        viewPager = findViewById(R.id.viewPager);

        //Configurar adapter para as abas
        FragmentPagerItemAdapter adapterAbasExerc = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Descrição", DescExercFragment.class)
                .add("Imagem", ImgExercFragment.class)
                .add("Vídeo", VideoExercFragment.class)
                .create()
        );
        viewPager.setAdapter(adapterAbasExerc);
        smartTabLayout.setViewPager(viewPager);
    }
}