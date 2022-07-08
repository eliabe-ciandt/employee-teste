package com.ciandt.feedfront.dao;

import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;
import com.ciandt.feedfront.interfaces.DAO;
import com.ciandt.feedfront.models.Feedback;

import java.util.List;

public class FeedbackDAO implements DAO<Feedback> {
    @Override
    public boolean tipoImplementaSerializable() {
        return false;
    }

    @Override
    public List<Feedback> listar() throws EntidadeNaoSerializavelException {
        return null;
    }

    @Override
    public Feedback buscar(String id) throws EntidadeNaoSerializavelException {
        return null;
    }

    @Override
    public Feedback salvar(Feedback feedback) throws EntidadeNaoSerializavelException {
        return null;
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws EntidadeNaoSerializavelException {
        return null;
    }

    @Override
    public void apagar(String id) throws EntidadeNaoSerializavelException {

    }
}
