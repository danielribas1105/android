package com.apps.mynotes;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apps.mynotes.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputEditText;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private MinhasAnotacoes minhasAnotacoes;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minhasAnotacoes = new MinhasAnotacoes(getApplicationContext());
        editText = findViewById(R.id.editNotes);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoSalvo = editText.getText().toString();
                if(textoSalvo.equals("")){
                    Snackbar.make(view, "Faça uma anotação!", Snackbar.LENGTH_LONG)
                            .show();
                }else {
                    minhasAnotacoes.salvarNotas(textoSalvo);
                    Snackbar.make(view, "Anotação salva com sucesso!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        String note = minhasAnotacoes.recuperarNotas();
        if(!note.equals("")){
            editText.setText(note);
        }
    }

}