package com.apps.drpersonalmanager.ui.activity;

import com.apps.drpersonalmanager.helper.Base64Custom;

public interface ConstantesActivities {

    String CHAVE_DB_PERSONAL = "personal";
    String CHAVE_DB_ALUNOS = "alunos";
    String CHAVE_DB_EXERCICIOS = "exercicios";
    String CHAVE_DB_TREINOS = "treinos";
    String CHAVE_DB_EXERCICIOS_ALUNOS = "exerciciosAlunos";
    String CHAVE_DB_IDPERSONAL = Base64Custom.codeToBase64("drpersonal@gmail.com");
    String CHAVE_ALUNO_SELECT = "idAluno";
    String CHAVE_ID_SERIE = "idSerie";
    String CHAVE_EXERCICIO_EDIT = "editExercise";
    String CHAVE_CATEGORIA = "catExerc";
    String CAT_AEROBICO = "aero";
    String CAT_ABDOMINAIS = "abdo";
    String CAT_MUSC_SUPER = "muscSuper";
    String CAT_MUSC_INFER = "muscInfer";

}
