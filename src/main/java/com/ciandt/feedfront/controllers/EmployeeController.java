package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.interfaces.Service;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.services.EmployeeService;

import java.io.Serializable;
import java.util.List;

public class EmployeeController implements Serializable {

    private Service<Employee> employeeService;

    public EmployeeController() {
        this.employeeService = new EmployeeService();
    }

    public List<Employee> listar() throws BusinessException, ArquivoException, EntidadeNaoSerializavelException {

        List<Employee> listaDeEmployees = employeeService.listar();
            return listaDeEmployees;

    }

    public Employee buscar(String id) throws BusinessException, ArquivoException, EmployeeNaoEncontradoException, EntidadeNaoSerializavelException {

        Employee resultadoDeBusca = employeeService.buscar(id);
            return resultadoDeBusca;

    }

    public Employee salvar(Employee employee) throws EmailInvalidoException, ArquivoException, BusinessException, EntidadeNaoSerializavelException {

            Employee employeeSalvo = employeeService.salvar(employee);
            return employeeSalvo;

    }

    public Employee atualizar(Employee employee) throws EmployeeNaoEncontradoException, EmailInvalidoException, ArquivoException {


            Employee employeeAtualizado = Employee.atualizarEmployee(employee);
            return employeeAtualizado;


    }

    public void apagar(String id) throws BusinessException, ArquivoException, EmployeeNaoEncontradoException, EntidadeNaoSerializavelException {

            employeeService.apagar(id);
            System.out.println("Employee Apagado!");

    }
}
