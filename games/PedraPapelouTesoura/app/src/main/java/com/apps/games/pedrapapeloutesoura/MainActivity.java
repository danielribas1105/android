package com.apps.games.pedrapapeloutesoura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectPedra(View view){
        this.selectUser("pedra");
    }
    public void selectPapel(View view){
        this.selectUser("papel");
    }
    public void selectTesoura(View view){
        this.selectUser("tesoura");
    }

    public void selectUser(String select){
        int n = new Random().nextInt(3); //0 1 2
        String[] opcoes = {"pedra","papel","tesoura"};
        String selectApp = opcoes[n];
        ImageView imageApp = findViewById(R.id.imageApp);
        TextView message = findViewById(R.id.escolhaUser);

        switch (selectApp) {
            case "pedra":
                imageApp.setImageResource(R.drawable.pedra);
                break;
            case "papel":
                imageApp.setImageResource(R.drawable.papel);
                break;
            case "tesoura":
                imageApp.setImageResource(R.drawable.tesoura);
                break;
        }

        if((selectApp=="pedra" && select=="tesoura")||
           (selectApp=="tesoura" && select=="papel")||
           (selectApp=="papel" && select=="pedra")){
           message.setText("Você perdeu :(");
        }else if((select=="pedra" && selectApp=="tesoura")||
                (select=="tesoura" && selectApp=="papel")||
                (select=="papel" && selectApp=="pedra")){
                message.setText("Você ganhou :)");
        }else{
            message.setText("Empatou! ;)");
        }

    }

}