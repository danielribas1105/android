package com.apps.drpersonalmanager.ui.activity;

import com.apps.drpersonalmanager.helper.Base64Custom;

public interface ConstantesActivities {

    String CHAVE_DB_PERSONAL = "personal";
    String CHAVE_DB_ALUNOS = "alunos";
    String CHAVE_DB_EXERCICIOS = "exercicios";
    String CHAVE_DB_TREINOS = "treinos";
    String CHAVE_DB_HISTORICO = "historico";
    String CHAVE_DB_EXERCICIOS_ALUNOS = "exerciciosAlunos";
    String CHAVE_DB_REPETICOES = "quantExerc";
    String CHAVE_DB_PESO = "pesoExerc";
    String CHAVE_DB_OBS_EXERC = "obsExerc";
    String CHAVE_DB_IDPERSONAL = Base64Custom.codeToBase64("drpersonal@gmail.com");
    String CHAVE_DB_NOME_PERSONAL = "nomePersonal";
    String CHAVE_ST_IMAGES = "images";
    String CHAVE_ST_EXERCISES = "exercises";
    String CHAVE_ST_PROFILE_PERSONAL = "profilePersonal";
    String CHAVE_ST_PROFILE_ALUNOS = "profileAlunos";
    String CHAVE_ST_ID_IMG_PADRAO = "https://firebasestorage.googleapis.com/v0/b/dr-personal-68282.appspot.com/o/images%2Fhelper%2Fpadrao.jpg?alt=media&token=0c631b17-d920-4e4f-aa19-bcf5f81c466f";
    String CHAVE_ALUNO_SELECT = "idAluno";
    String CHAVE_TREINO_SELECT = "treinoSelecionado";
    String CHAVE_ID_SERIE = "idSerie";
    String CHAVE_ID_OBJETIVO = "objetivo";
    String CHAVE_EXERCICIO_EDIT = "editExercise";
    String CHAVE_CATEGORIA = "catExerc";
    String STR_SERIE = "serie";
    String CAT_AEROBICO = "aero";
    String CAT_ABDOMINAIS = "abdo";
    String CAT_MUSC_SUPER = "muscSuper";
    String CAT_MUSC_INFER = "muscInfer";
    String IMAGE_NOT_FOUND = "https://firebasestorage.googleapis.com/v0/b/dr-personal-68282.appspot.com/o/images%2Fexercises%2Fimagem_indisponivel.jpg?alt=media&token=ea357387-2783-43e2-9616-d28834cb0256";

}
