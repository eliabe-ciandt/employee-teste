package com.ciandt.feedfront.dao;

import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;
import com.ciandt.feedfront.interfaces.DAO;
import com.ciandt.feedfront.models.Feedback;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeedbackDAO implements DAO<Feedback> {

    private static final String repositorioPath = "src/main/resources/data/feedback/";

    @Override
    public boolean tipoImplementaSerializable() {
        return (Feedback.class instanceof Serializable);
    }

    private static ObjectOutputStream getOutputStream(String arquivo) throws IOException {
        return new ObjectOutputStream(new FileOutputStream(arquivo));
    }

    private static ObjectInputStream getInputStream(String arquivo) throws IOException {
        return new ObjectInputStream(new FileInputStream(arquivo));
    }

    @Override
    public List<Feedback> listar() throws EntidadeNaoSerializavelException, IOException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        List<Feedback> feedbacks = new ArrayList<>();

        Stream<Path> paths = Files.walk(Paths.get(repositorioPath));

        List<String> files = paths
                .map(p -> p.getFileName().toString())
                .filter(p -> p.endsWith(".byte"))
                .map(p -> p.replace(".byte", ""))
                .collect(Collectors.toList());

        for (String file: files) {
            feedbacks.add(buscar(file));
        }

        paths.close();

        return feedbacks;
    }

    @Override
    public Feedback buscar(String id) throws EntidadeNaoSerializavelException, IOException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        Feedback feedback;
        ObjectInputStream inputStream;

        inputStream = getInputStream(repositorioPath + id + ".byte");
        try {
            feedback = (Feedback) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }

        inputStream.close();

        return feedback;
    }

    @Override
    public Feedback salvar(Feedback feedback) throws EntidadeNaoSerializavelException, IOException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        ObjectOutputStream outputStream = null;
        List<Feedback> feedbacks = listar();

        outputStream = getOutputStream(repositorioPath + feedback.getArquivo());
        outputStream.writeObject(feedback);
        outputStream.close();

        return feedback;
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws EntidadeNaoSerializavelException, IOException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        buscar(feedback.getId());
        return salvar(feedback);
    }

    @Override
    public boolean apagar(String id) throws EntidadeNaoSerializavelException, IOException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        buscar(id);
        new File(String.format("%s%s.byte", repositorioPath, id)).delete();

        return true;
    }
}
