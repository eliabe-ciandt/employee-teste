package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.interfaces.Service;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.services.EmployeeService;

import java.io.Serializable;
import java.util.List;

public class EmployeeController {

    private Service<Employee> employeeService;

    public EmployeeController() {
        this.employeeService = new EmployeeService();
    }

    public List<Employee> listar() throws ArquivoException {

        List<Employee> listaDeEmployees = employeeService.listar();
            return listaDeEmployees;

    }

    public Employee buscar(String id) throws ArquivoException, BusinessException {

        Employee resultadoDeBusca = employeeService.buscar(id);
            return resultadoDeBusca;

    }

    public Employee salvar(Employee employee) throws ArquivoException, BusinessException{

            Employee employeeSalvo = employeeService.salvar(employee);
            return employeeSalvo;

    }

    public Employee atualizar(Employee employee) throws ArquivoException, BusinessException {


            Employee employeeAtualizado = employeeService.atualizar(employee);
            return employeeAtualizado;


    }

    public void apagar(String id) throws ArquivoException, BusinessException {

            employeeService.apagar(id);
            System.out.println("Employee Apagado!");

    }
}
