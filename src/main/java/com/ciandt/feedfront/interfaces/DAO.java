package com.ciandt.feedfront.interfaces;

import com.ciandt.feedfront.excecoes.*;

import java.io.IOException;
import java.util.List;

public interface DAO<E> {

    boolean tipoImplementaSerializable();
    List<E> listar() throws EntidadeNaoSerializavelException, IOException;
    E buscar (String id) throws EntidadeNaoSerializavelException, IOException;
    E salvar(E e) throws EntidadeNaoSerializavelException, IOException;
    E atualizar(E e) throws EntidadeNaoSerializavelException, IOException;
    boolean apagar(String id) throws EntidadeNaoSerializavelException, IOException;

}
