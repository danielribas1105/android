package com.apps.drpersonal.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.drpersonal.R;
import com.apps.drpersonal.helper.DataCustom;

public class HistoricoActivity extends AppCompatActivity {

    private TextView prevMonth, monthYear, nextMonth;
    private String currentMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        prevMonth = findViewById(R.id.textPrevMonth);
        monthYear = findViewById(R.id.textMonthYear);
        nextMonth = findViewById(R.id.textNextMonth);
        monthYear.setText(DataCustom.mesAnoFormat(DataCustom.dataAtual()));

    }

    public void clickPreviousMonth(View view) {
        currentMonth = monthYear.getText().toString();
        monthYear.setText(DataCustom.previousMonth(currentMonth));
        //movimentacoesRef.removeEventListener(valueEventListenerMovimentacoes);
        //mesAno = DataCustom.previousMonthDataBase(currentMonth);
        //getMovimentacoes();
    }

    public void clickNextMonth(View view) {
        currentMonth = monthYear.getText().toString();
        monthYear.setText(DataCustom.nextMonth(currentMonth));
        //movimentacoesRef.removeEventListener(valueEventListenerMovimentacoes);
        //mesAno = DataCustom.nextMonthDataBase(currentMonth);
        //getMovimentacoes();
    }

}