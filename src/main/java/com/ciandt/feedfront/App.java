package com.ciandt.feedfront;

import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Employee;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws ComprimentoInvalidoException, EmailInvalidoException, ArquivoException, EmployeeNaoEncontradoException {

        Employee novoEmployee1 = new Employee("Joao","Santos","joaosantos@mail.com");
        Employee novoEmployee2 = new Employee("Maria","Duarte","mariaduarte@mail.com");
        Employee novoEmployee3 = new Employee("Matheus","Cavalcante","matheuscavalcante@mail.com");
        Employee novoEmployee4 = new Employee("Daniel","Cerqueira","danielcerqueira@mail.com");
        Employee novoEmployee5 = new Employee("Rafael","Lopes","rafaellopes@mail.com");
        Employee novoEmployee6 = new Employee("Thiago","Dutra","thiagodutra@mail.com");

        System.out.println("-------------------------------");
        try {
            try {
                novoEmployee1.getEmployeeController().salvar(novoEmployee1);
                novoEmployee2.getEmployeeController().salvar(novoEmployee2);
                novoEmployee3.getEmployeeController().salvar(novoEmployee3);
                novoEmployee4.getEmployeeController().salvar(novoEmployee4);
                novoEmployee5.getEmployeeController().salvar(novoEmployee5);
                novoEmployee6.getEmployeeController().salvar(novoEmployee6);
                System.out.println("Criacao de Employee feito com sucesso!");
            } catch (BusinessException e) {
                System.out.println("Deu ruim");
            } catch (EntidadeNaoSerializavelException e) {
                e.getCause();
            }
        } catch (EmailInvalidoException | ArquivoException ex) {
            ex.getCause();
        }
        System.out.println("-------------------------------");
        System.out.println("Lista de Employees:");
        try {
            novoEmployee1.getEmployeeController().listar().forEach(employee -> System.out.println(employee.toString()));
        } catch (BusinessException e) {
            e.getMessage();
        } catch (EntidadeNaoSerializavelException e) {
            e.getCause();
        }

        System.out.println("-------------------------------");

        try {
            System.out.println("Busca com Employee com id: " + novoEmployee1.getId());
            System.out.println(Employee.buscarEmployee(novoEmployee1.getId()).toString());

        } catch (EmployeeNaoEncontradoException | ArquivoException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("-------------------------------");
        try {
            novoEmployee1.setNome("Jose");
            novoEmployee1.setSobrenome("da Silva");

            Employee atualizaEmployee = novoEmployee2.getEmployeeController().salvar(novoEmployee1);
            System.out.println("Employee Atualizado: "+ atualizaEmployee.toString());
        } catch (EmailInvalidoException | ArquivoException ex) {
            System.out.println(ex.getMessage());
        } catch (BusinessException e) {
            e.getMessage();
        } catch (EntidadeNaoSerializavelException e) {
            e.getCause();
        }

        System.out.println("-------------------------------");
        try {
            novoEmployee2.getEmployeeController().apagar(novoEmployee1.getId());
            System.out.println("Cadastro Deletado com sucesso!");
        } catch (EmployeeNaoEncontradoException | BusinessException ex) {
            System.out.println(ex.getMessage());
        } catch (EntidadeNaoSerializavelException e) {
            e.getCause();
        }

    }
}
