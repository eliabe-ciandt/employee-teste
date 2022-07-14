package com.ciandt.feedfront.services;

import com.ciandt.feedfront.dao.FeedbackDAO;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.interfaces.Service;
import com.ciandt.feedfront.models.Feedback;

import java.io.IOException;
import java.util.List;

public class FeedbackService implements Service<Feedback> {

    private FeedbackDAO feedbackDAO;

    public FeedbackService() {
        this.feedbackDAO = new FeedbackDAO();
    }

    @Override
    public List<Feedback> listar() throws ArquivoException {
        try {
            List<Feedback> feedbacks = feedbackDAO.listar();
            return feedbacks;
        } catch (IOException e) {
            throw new ArquivoException(e);
        }
    }

    @Override
    public Feedback buscar(String id) throws ArquivoException, BusinessException {
        try {
            Feedback feedbackEncontrado = feedbackDAO.buscar(id);
            return feedbackEncontrado;
        } catch (IOException e) {
            throw new ArquivoException(e);
        }
    }

    @Override
    public Feedback salvar(Feedback feedback) throws ArquivoException, BusinessException {
        try {
            feedbackDAO.salvar(feedback);
        } catch (IOException e) {
            throw new ArquivoException(e);
        }
        return feedback;
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws ArquivoException, BusinessException {
        try {
            feedbackDAO.atualizar(feedback);
        } catch (IOException e) {
            throw new ArquivoException(e);
        }
        return feedback;
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException {
        try {
            feedbackDAO.apagar(id);
        } catch (IOException e) {
            throw new ArquivoException(e);
        }
    }
}
