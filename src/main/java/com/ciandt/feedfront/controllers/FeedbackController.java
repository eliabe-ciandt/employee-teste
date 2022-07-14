package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.models.Feedback;
import com.ciandt.feedfront.services.FeedbackService;

import java.util.List;

public class FeedbackController {

    private FeedbackService feedbackService;

    public FeedbackController() {
        this.feedbackService = new FeedbackService();
    }

    public List<Feedback> listar() throws ArquivoException {
        List<Feedback> feedbacks = feedbackService.listar();
        return feedbacks;
    }

    public Feedback buscar(String id) throws ArquivoException, BusinessException {
        Feedback feedbackEncontrado = feedbackService.buscar(id);
        return feedbackEncontrado;
    }

    public Feedback salvar(Feedback feedback) throws ArquivoException, BusinessException {
        feedbackService.salvar(feedback);
        return feedback;
    }

    public Feedback atualizar(Feedback feedback) throws ArquivoException, BusinessException {
        feedbackService.atualizar(feedback);
        return feedback;
    }

    public void apagar(String id) throws ArquivoException, BusinessException {
        feedbackService.apagar(id);
    }
}
