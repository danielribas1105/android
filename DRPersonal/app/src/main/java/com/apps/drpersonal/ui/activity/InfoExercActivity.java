package com.apps.drpersonal.ui.activity;

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.apps.drpersonal.R;
import com.apps.drpersonal.model.Exercise;
import com.apps.drpersonal.ui.fragment.DescExercFragment;
import com.apps.drpersonal.ui.fragment.ImgExercFragment;
import com.apps.drpersonal.ui.fragment.VideoExercFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class InfoExercActivity extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private Exercise exercSelected;
    private static String nomeExerc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_exerc);

        exercSelected = (Exercise) getIntent().getSerializableExtra("nomeExercicio");
        if(exercSelected!=null){
            nomeExerc = exercSelected.getNomeExerc();
        }

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