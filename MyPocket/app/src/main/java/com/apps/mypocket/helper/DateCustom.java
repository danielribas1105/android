package com.apps.mypocket.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual(){
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtual = simpleDateFormat.format(data);
        return dataAtual;
    }

    public static String dataMesAno(String data){
        String diaMesAno[] = data.split("/");
        String dia = diaMesAno[0];
        String mes = diaMesAno[1];
        String ano = diaMesAno[2];
        return mes+ano;
    }
}
