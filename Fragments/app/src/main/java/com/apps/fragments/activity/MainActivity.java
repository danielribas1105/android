package com.apps.fragments.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apps.fragments.R;
import com.apps.fragments.fragment.ContatosFragment;
import com.apps.fragments.fragment.ConversasFragment;

public class MainActivity extends AppCompatActivity {

    private Button btnConversa, btnContato;
    private ConversasFragment conversasFragment;
    private ContatosFragment contatosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConversa = findViewById(R.id.btnConversas);
        btnContato = findViewById(R.id.btnContatos);

        getSupportActionBar().setElevation(0);

        btnConversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conversasFragment = new ConversasFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, conversasFragment);
                transaction.commit();
            }
        });

        btnContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contatosFragment = new ContatosFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, contatosFragment);
                transaction.commit();
            }
        });

    }
}