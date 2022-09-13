package com.apps.listadetarefas.helper;

import com.apps.listadetarefas.model.Task;

import java.util.List;

public interface ITaskDAO {

    public boolean salvar(Task task);
    public boolean atualizar(Task task);
    public boolean deletar(Task task);
    public List<Task> listar();
}
