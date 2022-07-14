package com.ciandt.feedfront.dao;

import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;
import com.ciandt.feedfront.interfaces.DAO;
import com.ciandt.feedfront.models.Employee;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeDAO implements DAO<Employee>, Serializable {

    private static final String repositorioPath = "src/main/resources/data/employee/";

    public EmployeeDAO() {

    }

    @Override
    public boolean tipoImplementaSerializable() {
        return (Employee.class instanceof Serializable);
    }

    private static ObjectOutputStream getOutputStream(String arquivo) throws IOException {
        return new ObjectOutputStream(new FileOutputStream(arquivo));
    }

    private static ObjectInputStream getInputStream(String arquivo) throws IOException {
        return new ObjectInputStream(new FileInputStream(arquivo));
    }

    @Override
    public List<Employee> listar() throws EntidadeNaoSerializavelException, IOException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        List<Employee> employees = new ArrayList<>();


            Stream<Path> paths = Files.walk(Paths.get(EmployeeDAO.getRepositorioPath()));

            List<String> files = paths
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".byte"))
                    .map(p -> p.replace(".byte", ""))
                    .collect(Collectors.toList());

            for (String file: files) {
                employees.add(buscar(file));
            }

            paths.close();

        return employees;
    }

    @Override
    public Employee buscar(String id) throws EntidadeNaoSerializavelException, IOException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        Employee employee;
        ObjectInputStream inputStream;

            inputStream = getInputStream(EmployeeDAO.getRepositorioPath() + id + ".byte");
        try {
            employee = (Employee) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }

        inputStream.close();

        return employee;

    }

    @Override
    public Employee salvar(Employee employee) throws EntidadeNaoSerializavelException, IOException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        ObjectOutputStream outputStream = null;

            List<Employee> employees = listar();

            outputStream = getOutputStream(EmployeeDAO.getRepositorioPath() + employee.getArquivo());
            outputStream.writeObject(employee);

            outputStream.close();
        return employee;

    }

    @Override
    public Employee atualizar(Employee employee) throws EntidadeNaoSerializavelException, IOException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        buscar(employee.getId());

        return salvar(employee);
    }

    @Override
    public boolean apagar(String id) throws EntidadeNaoSerializavelException, IOException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        buscar(id);

        new File(String.format("%s%s.byte", EmployeeDAO.getRepositorioPath(), id)).delete();
        return true;
    }

    public static String getRepositorioPath() {
        return repositorioPath;
    }
}
