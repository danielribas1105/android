package com.apps.drpersonaltrainer.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apps.drpersonaltrainer.R;
import com.apps.drpersonaltrainer.databinding.ActivityHomeBinding;
import com.apps.drpersonaltrainer.ui.fragment.BuscarAlunoFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // Cria referência para toda a área do NavigationDrawer
        DrawerLayout drawer = binding.drawerLayout;

        // Cria referência para a área de navegação
        NavigationView navigationView = binding.navView;

        // Define as configurações para o NvigationDrawer
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_buscar_alunos, R.id.nav_new_treino, R.id.nav_editar_treino,
                R.id.nav_new_exercicio, R.id.nav_editar_exercicio)
                .setOpenableLayout(drawer)
                .build();

        // Configura a área que irá carregar os fragments
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);

        // Configura o menu superior esquerdo da navegação
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        // Configura a navegação para o menu NavigationView
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}