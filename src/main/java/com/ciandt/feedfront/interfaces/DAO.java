package com.ciandt.feedfront.interfaces;

import com.ciandt.feedfront.excecoes.*;

import java.util.List;

public interface DAO<E> {

    boolean tipoImplementaSerializable();
    List<E> listar() throws EntidadeNaoSerializavelException, BusinessException, ArquivoException;
    E buscar (String id) throws EntidadeNaoSerializavelException, BusinessException, ArquivoException, EmployeeNaoEncontradoException;
    E salvar(E e) throws EntidadeNaoSerializavelException, EmailInvalidoException, ArquivoException, BusinessException;
    E atualizar(E e) throws EntidadeNaoSerializavelException, EmployeeNaoEncontradoException, EmailInvalidoException, ArquivoException, BusinessException;
    void apagar(String id) throws EntidadeNaoSerializavelException, BusinessException, ArquivoException, EmployeeNaoEncontradoException;

}
