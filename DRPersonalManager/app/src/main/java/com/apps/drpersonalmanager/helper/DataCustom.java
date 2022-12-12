package com.apps.drpersonalmanager.helper;

import java.text.SimpleDateFormat;

public class DataCustom {

    private static String[] month = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private static long data;

    public static String dataAtual() {
        data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtual = simpleDateFormat.format(data);
        return dataAtual;
    }

    public static String dataMesAno(String data) {
        String diaMesAno[] = data.split("/");
        String mes = diaMesAno[1];
        String ano = diaMesAno[2];
        return String.format("%02d",Integer.parseInt(mes)) + ano;
    }

    public static String diaAtual(String data){
        String diaMesAno[] = data.split("/");
        String dia = diaMesAno[0];
        return String.format("%02d",Integer.parseInt(dia));
    }

    public static String mesAnoFormat(String data) {
        String diaMesAno[] = data.split("/");
        Integer numMes = Integer.parseInt(diaMesAno[1]) - 1;
        String mes = month[numMes];
        String ano = diaMesAno[2];
        return mes + " " + ano;
    }

    public static String previousMonth(String data) {
        String mesAno[] = data.split(" ");
        Integer ano = Integer.valueOf(mesAno[1]);
        if(mesAno[0].equals("Janeiro")){
            mesAno[0] = "Dezembro";
            mesAno[1] = String.valueOf(ano = ano - 1);
        }else {
            for (int i = 0; i < 12; i++) {
                if (mesAno[0].equals(month[i])) {
                    mesAno[0] = month[i - 1];
                    break;
                }
            }
        }
        return mesAno[0] + " " + mesAno[1];
    }

    public static String previousMonthDataBase(String mesAtual){
        String mesAno[] = mesAtual.split(" ");
        Integer prevMonth = 0;
        Integer ano = Integer.valueOf(mesAno[1]);
        if(mesAno[0].equals("Janeiro")){
            prevMonth = 12;
            mesAno[1] = String.valueOf(ano = ano - 1);
        }else{
            for (int i = 0; i < 12; i++) {
                if (mesAno[0].equals(month[i])) {
                    prevMonth = i;
                    break;
                }
            }
        }
        return String.format("%02d",prevMonth) + mesAno[1];
    }

    public static String nextMonth(String data) {
        String mesAno[] = data.split(" ");
        Integer ano = Integer.valueOf(mesAno[1]);
        if(mesAno[0].equals("Dezembro")){
            mesAno[0] = "Janeiro";
            mesAno[1] = String.valueOf(ano = ano + 1);
        }else{
            for (int i = 0; i < 12; i++) {
                if (mesAno[0].equals(month[i])) {
                    mesAno[0] = month[i + 1];
                    break;
                }
            }
        }
        return mesAno[0] + " " + mesAno[1];
    }

    public static String nextMonthDataBase(String mesAtual) {
        String mesAno[] = mesAtual.split(" ");
        Integer nextMonth = 0;
        Integer ano = Integer.valueOf(mesAno[1]);
        if(mesAno[0].equals("Dezembro")){
            nextMonth = 1;
            mesAno[1] = String.valueOf(ano = ano + 1);
        }else{
            for (int i = 0; i < 12; i++) {
                if (mesAno[0].equals(month[i])) {
                    nextMonth = i + 2;
                    break;
                }
            }
        }
        return String.format("%02d",nextMonth) + mesAno[1];
    }

}
