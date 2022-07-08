package com.ciandt.feedfront.dao;

import com.ciandt.feedfront.excecoes.*;
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
    public List<Employee> listar() throws EntidadeNaoSerializavelException, BusinessException, ArquivoException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        List<Employee> employees = new ArrayList<>();

        try {
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
        } catch (IOException e) {
            throw new ArquivoException("");
        } catch (EmployeeNaoEncontradoException e) {
            e.getMessage();
        }

        return employees;
    }

    @Override
    public Employee buscar(String id) throws EntidadeNaoSerializavelException, BusinessException, ArquivoException, EmployeeNaoEncontradoException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        Employee employee;
        ObjectInputStream inputStream;

        try {
            inputStream = getInputStream(EmployeeDAO.getRepositorioPath() + id + ".byte");
            employee = (Employee) inputStream.readObject();

            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            if (e.getClass().getSimpleName().equals("FileNotFoundException")) {
                throw new EmployeeNaoEncontradoException("Employee não encontrado");
            }

            throw new ArquivoException("");
        }

        return employee;

    }

    @Override
    public Employee salvar(Employee employee) throws EntidadeNaoSerializavelException, EmailInvalidoException, ArquivoException, BusinessException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        ObjectOutputStream outputStream = null;

        try {
            List<Employee> employees = listar();

            boolean emailExistente = false;
            for (Employee employeeSalvo: employees) {
                if (!employeeSalvo.getId().equals(employee.getId()) && employeeSalvo.getEmail().equals(employee.getEmail())) {
                    emailExistente = true;
                    break;
                }
            }

            if (emailExistente) {
                throw new EmailInvalidoException("E-mail ja cadastrado no repositorio");
            }

            outputStream = getOutputStream(EmployeeDAO.getRepositorioPath() + employee.getArquivo());
            outputStream.writeObject(employee);

            outputStream.close();
        } catch (IOException | EmailInvalidoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ArquivoException("");
        }

        return employee;

    }

    @Override
    public Employee atualizar(Employee employee) throws EntidadeNaoSerializavelException, EmployeeNaoEncontradoException, EmailInvalidoException, ArquivoException, BusinessException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        buscar(employee.getId());

        return salvar(employee);
    }

    @Override
    public void apagar(String id) throws EntidadeNaoSerializavelException, BusinessException, ArquivoException, EmployeeNaoEncontradoException {
        if(!tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Serializa, mula");
        }

        buscar(id);

        new File(String.format("%s%s.byte", EmployeeDAO.getRepositorioPath(), id)).delete();
    }

    public static String getRepositorioPath() {
        return repositorioPath;
    }
}
