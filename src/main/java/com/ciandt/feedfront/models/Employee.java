package com.ciandt.feedfront.models;

import com.ciandt.feedfront.controllers.EmployeeController;
import com.ciandt.feedfront.dao.EmployeeDAO;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Employee implements Serializable {

    private static final long serialVersionID = 1;
    private String id;
    private String nome;
    private String sobrenome;
    private String email;
    private String arquivo;

    private EmployeeController employeeController;


    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        this.id = UUID.randomUUID().toString();
        this.arquivo = getId() + ".byte";
        this.employeeController = new EmployeeController();

        setNome(nome);
        setEmail(email);
        setSobrenome(sobrenome);
    }

    private static ObjectOutputStream getOutputStream(String arquivo) throws IOException {
        return new ObjectOutputStream(new FileOutputStream(arquivo));
    }

    private static ObjectInputStream getInputStream(String arquivo) throws IOException {
        return new ObjectInputStream(new FileInputStream(arquivo));
    }

    public static Employee salvarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        ObjectOutputStream outputStream = null;

        try {
            List<Employee> employees = listarEmployees();

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

            outputStream = getOutputStream(employee.arquivo);
            outputStream.writeObject(employee);

            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ArquivoException("");
        }

        return employee;
    }

    public static Employee atualizarEmployee(Employee employee) throws ArquivoException, EmployeeNaoEncontradoException, EmailInvalidoException {
        buscarEmployee(employee.getId());

        return salvarEmployee(employee);
    }

    public static List<Employee> listarEmployees() throws ArquivoException {
        List<Employee> employees = new ArrayList<>();

        try {
            Stream<Path> paths = Files.walk(Paths.get(EmployeeDAO.getRepositorioPath()));

            List<String> files = paths
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".byte"))
                    .map(p -> p.replace(".byte", ""))
                    .collect(Collectors.toList());

            for (String file: files) {
                try {
                    employees.add(buscarEmployee(file));
                } catch (EmployeeNaoEncontradoException e) {
                    e.getMessage();
                }
            }

            paths.close();
        } catch (IOException e) {
            throw new ArquivoException("");
        }

        return employees;
    }

    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
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

    public static void apagarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        buscarEmployee(id);

        new File(String.format("%s%s.byte", EmployeeDAO.getRepositorioPath(), id)).delete();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Employee employee = (Employee) obj;

        return getId().equals(employee.getId());
    }

    @Override
    public int hashCode() {
        return 31 * 7 + ((id == null) ?  0 : id.hashCode());
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ComprimentoInvalidoException {

        if (nome.length() < 3) {
            throw new ComprimentoInvalidoException("Comprimento do nome deve ser maior que 2 caracteres.");
        }

        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {

        if (sobrenome.length() < 3) {
            throw new ComprimentoInvalidoException("Comprimento do sobrenome deve ser maior que 2 caracteres.");
        }

        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getArquivo() {
        return arquivo;
    }

    public EmployeeController getEmployeeController() {
        return employeeController;
    }

    @Override
    public String toString() {
        return getNome() + " " + getSobrenome() + " - " + getEmail();
    }
}
