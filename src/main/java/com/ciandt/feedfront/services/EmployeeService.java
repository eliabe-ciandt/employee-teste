package com.ciandt.feedfront.services;

import com.ciandt.feedfront.dao.EmployeeDAO;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.interfaces.DAO;
import com.ciandt.feedfront.interfaces.Service;
import com.ciandt.feedfront.models.Employee;

import java.io.Serializable;
import java.util.List;

public class EmployeeService implements Service<Employee>, Serializable {

    private DAO<Employee> dao;

    public EmployeeService() {
        this.dao = new EmployeeDAO();
    }


    @Override
    public List<Employee> listar() throws ArquivoException, BusinessException, EntidadeNaoSerializavelException {
        List<Employee> employees = dao.listar();
        return employees;
    }

    @Override
    public Employee buscar(String id) throws ArquivoException, BusinessException, EmployeeNaoEncontradoException, EntidadeNaoSerializavelException {
        Employee employeeBuscado = dao.buscar(id);
        return employeeBuscado;
    }

    @Override
    public Employee salvar(Employee employee) throws ArquivoException, BusinessException, EmailInvalidoException, EntidadeNaoSerializavelException {
        Employee employeeSalvo = dao.salvar(employee);
        return employeeSalvo;
    }

    @Override
    public Employee atualizar(Employee employee) throws ArquivoException, BusinessException, EmployeeNaoEncontradoException, EmailInvalidoException, EntidadeNaoSerializavelException {
        Employee employeeAtualizado = dao.atualizar(employee);
        return employeeAtualizado;
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException, EmployeeNaoEncontradoException, EntidadeNaoSerializavelException {
        dao.apagar(id);
    }

}
