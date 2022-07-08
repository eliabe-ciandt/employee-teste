package com.ciandt.feedfront.interfaces;

import com.ciandt.feedfront.excecoes.*;

import java.util.List;

public interface Service<E> {

    List<E> listar() throws ArquivoException, BusinessException, EntidadeNaoSerializavelException;
    E buscar(String id) throws ArquivoException, BusinessException, EmployeeNaoEncontradoException, EntidadeNaoSerializavelException;
    E salvar(E e) throws ArquivoException, BusinessException, EmailInvalidoException, EntidadeNaoSerializavelException;
    E atualizar(E e) throws ArquivoException, BusinessException, EmployeeNaoEncontradoException, EmailInvalidoException, EntidadeNaoSerializavelException;
    void apagar(String id) throws ArquivoException, BusinessException, EmployeeNaoEncontradoException, EntidadeNaoSerializavelException;
}
