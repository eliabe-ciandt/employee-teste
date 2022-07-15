package com.ciandt.feedfront.services;

import com.ciandt.feedfront.dao.EmployeeDAO;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.interfaces.Service;
import com.ciandt.feedfront.models.Employee;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class EmployeeService implements Service<Employee> {

    private EmployeeDAO dao;

    public EmployeeService() {
        this.dao = new EmployeeDAO();
    }


    @Override
    public List<Employee> listar() throws ArquivoException {
        List<Employee> employees = null;
        try {
            employees = dao.listar();
        } catch (IOException e) {
            throw new ArquivoException(e);
        }
        return employees;
    }

    @Override
    public Employee buscar(String id) throws ArquivoException, BusinessException {
        Employee employeeBuscado = null;

        try {
            employeeBuscado = dao.buscar(id);
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("Employee nao encontrado");
        }

        return employeeBuscado;
    }

    @Override
    public Employee salvar(Employee employee) throws ArquivoException, BusinessException {
        Employee employeeSalvo = null;

        try {
            List<Employee> employees = dao.listar();
            for(Employee employee1: employees) {
                if(employee.getEmail().equals(employee1.getEmail())) {
                    throw new EmailInvalidoException("Email ja cadastrado");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            employeeSalvo = dao.salvar(employee);
        } catch (IOException e) {
            throw new ArquivoException(e);
        }
        return employeeSalvo;
    }

    @Override
    public Employee atualizar(Employee employee) throws ArquivoException, BusinessException {
        try {
            dao.buscar(employee.getId());
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("Employee nao encontrado");
        }
        return salvar(employee);
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException{
        try {
            dao.buscar(id);
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("Employee nao encontrado");
        }

        try {
            dao.apagar(id);
        } catch (IOException e) {
            throw new ArquivoException(e);
        }
    }

}
