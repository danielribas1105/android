package com.apps.whatsup.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.apps.whatsup.R;
import com.apps.whatsup.config.ConfigFirebase;
import com.apps.whatsup.ui.fragments.ContatosFragment;
import com.apps.whatsup.ui.fragments.ConversasFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        smartTabLayout = findViewById(R.id.viewPagerTab);
        viewPager = findViewById(R.id.viewPager);
        auth = ConfigFirebase.getFirebaseAutenticacao();

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        toolbar.setTitle("WhatsUp");
        setSupportActionBar(toolbar);

        FragmentPagerItemAdapter adapterFrag = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Conversas", ConversasFragment.class)
                .add("Contatos", ContatosFragment.class)
                .create()
        );

        viewPager.setAdapter(adapterFrag);
        smartTabLayout.setViewPager(viewPager);
        getSupportActionBar().setElevation(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSair:
                deslogarUser();
                finish();
                break;
            case R.id.menuEditar:
                editUser();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void editUser() {
        startActivity(new Intent(HomeActivity.this, EditarPerfilActivity.class));
    }

    public void deslogarUser(){
        try {
            auth.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}