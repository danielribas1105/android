package com.apps.personalvirtual.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.apps.personalvirtual.R;

public class MembrosSuperiores extends AppCompatActivity {

    private ListView exercSuper;
    private String[] tiposSuper = {
            "Peito","Costas","Ombros","Bíceps","Tríceps","Antebraço"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membros_superiores);

        exercSuper = findViewById(R.id.membrosSuperiores);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                tiposSuper
        );
        exercSuper.setAdapter(adapter);
    }
}