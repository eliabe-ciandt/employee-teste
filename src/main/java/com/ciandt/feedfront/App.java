package com.ciandt.feedfront;

import com.ciandt.feedfront.controllers.EmployeeController;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Employee;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws ComprimentoInvalidoException, EmailInvalidoException, ArquivoException, EntidadeNaoEncontradaException {

        EmployeeController employeeController = new EmployeeController();
        Employee novoEmployee1 = new Employee("Joao","Santos","joaosantos@mail.com");
        Employee novoEmployee2 = new Employee("Maria","Duarte","mariaduarte@mail.com");
        Employee novoEmployee3 = new Employee("Matheus","Cavalcante","matheuscavalcante@mail.com");
        Employee novoEmployee4 = new Employee("Daniel","Cerqueira","danielcerqueira@mail.com");
        Employee novoEmployee5 = new Employee("Rafael","Lopes","rafaellopes@mail.com");
        Employee novoEmployee6 = new Employee("Thiago","Dutra","thiagodutra@mail.com");

        System.out.println("-------------------------------");
        try {
            employeeController.salvar(novoEmployee1);
            employeeController.salvar(novoEmployee2);
            employeeController.salvar(novoEmployee3);
            employeeController.salvar(novoEmployee4);
            employeeController.salvar(novoEmployee5);
            employeeController.salvar(novoEmployee6);
            System.out.println("Criacao de Employee feito com sucesso!");
        } catch (BusinessException e) {
            System.out.println("Deu ruim");
        } catch (EntidadeNaoSerializavelException e) {
            e.getCause();
        }
        System.out.println("-------------------------------");
        System.out.println("Lista de Employees:");
        try {
            employeeController.listar().forEach(employee -> System.out.println(employee.toString()));
        } catch (BusinessException e) {
            e.getMessage();
        } catch (EntidadeNaoSerializavelException e) {
            e.getCause();
        }

        System.out.println("-------------------------------");

        try {
            System.out.println("Busca com Employee com id: " + novoEmployee1.getId());
            System.out.println(employeeController.buscar(novoEmployee1.getId()).toString());

        } catch (EntidadeNaoEncontradaException | ArquivoException ex) {
            System.out.println(ex.getMessage());
        } catch (BusinessException e) {
            e.getMessage();
        }

        System.out.println("-------------------------------");
        try {
            novoEmployee1.setNome("Jose");
            novoEmployee1.setSobrenome("da Silva");

            Employee atualizaEmployee = employeeController.salvar(novoEmployee1);
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
            employeeController.apagar(novoEmployee1.getId());
            System.out.println("Cadastro Deletado com sucesso!");
        } catch (BusinessException ex) {
            System.out.println(ex.getMessage());
        } catch (EntidadeNaoSerializavelException e) {
            e.getCause();
        }

    }
}
