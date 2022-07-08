package com.ciandt.feedfront.services;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.interfaces.Service;
import com.ciandt.feedfront.models.Feedback;

import java.util.List;

public class FeedbackService implements Service<Feedback> {
    @Override
    public List<Feedback> listar() throws ArquivoException, BusinessException {
        return null;
    }

    @Override
    public Feedback buscar(String id) throws ArquivoException, BusinessException {
        return null;
    }

    @Override
    public Feedback salvar(Feedback feedback) throws ArquivoException, BusinessException {
        return null;
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws ArquivoException, BusinessException {
        return null;
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException {

    }
}
