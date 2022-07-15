package com.ciandt.feedfront.services;


//import com.ciandt.feedfront.contracts.Service;

import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.interfaces.Service;
import com.ciandt.feedfront.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {
    private Employee employee;
    private Service<Employee> service = new EmployeeService();

    @BeforeEach
    public void initEach() {
        try {
            Files.walk(Paths.get("src/main/resources/data/employee/"))
                    .filter(p -> p.toString().endsWith(".byte"))
                    .forEach(p -> {
                        new File(p.toString()).delete();
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            employee = new Employee("João", "Silveira", "j.silveira@email.com");
        } catch (ComprimentoInvalidoException ignored) {
        }
    }

    @Test
    public void listar() {
        List<Employee> employees = assertDoesNotThrow(service::listar);

        assertTrue(employees.isEmpty());

        assertDoesNotThrow(() -> service.salvar(employee));

        employees = assertDoesNotThrow(service::listar);

        assertFalse(employees.isEmpty());
        assertTrue(employees.contains(employee));
        assertEquals(1, employees.size());
    }

    // Nota: esses dois métodos estão testando o "buscar" do service
    // Mas estão separados para reforçar a independência dos testes como manda o padrão FIRST
    @Test
    public void buscarMalSucedida() {
        String uuid = "11f2105a-4f5b-4a48-bf57-3a4ff8b477b1";
        assertThrows(EntidadeNaoEncontradaException.class, () -> service.buscar(uuid));
    }

    @Test
    public void buscarBemSucedida() {
        String uuid = employee.getId();
        assertDoesNotThrow(() -> service.salvar(employee));

        assertDoesNotThrow(() -> service.buscar(uuid));
    }

    @Test
    public void salvar() {
        Employee employeeInvalido = assertDoesNotThrow(
                () -> new Employee("José", "Silveira", "j.silveira@email.com")
        );

        assertDoesNotThrow(() -> service.salvar(employee));
        assertThrows(EmailInvalidoException.class, () -> service.salvar(employeeInvalido));
    }

    @Test
    public void atualizar() {
        Employee employee2 = assertDoesNotThrow(
                () -> new Employee("Bruno", "Silveira", "b.silveira@email.com")
        );
        Employee employee3 = assertDoesNotThrow(
                () -> new Employee("Vitor", "Fernandes", "vf.silveira@email.com")
        );

        assertDoesNotThrow(() -> service.salvar(employee));
        assertDoesNotThrow(() -> service.salvar(employee2));

        employee.setEmail("joao.silveira@email.com");
        Employee employeeSalvo = assertDoesNotThrow(() -> service.atualizar(employee));

        assertEquals(employee, employeeSalvo);

        try {
            employee2.setNome("Jean");
            employee2.setEmail("joao.silveira@email.com");
        } catch (ComprimentoInvalidoException ignored) {
        }

        assertThrows(EmailInvalidoException.class, () -> service.atualizar(employee2));
        assertThrows(EntidadeNaoEncontradaException.class, () -> service.atualizar(employee3));
    }

    @Test
    public void apagar() {
        assertDoesNotThrow(() -> service.salvar(employee));

        Employee employee2 = assertDoesNotThrow(
                () -> new Employee("Bruno", "Silveira", "b.silveira@email.com")
        );

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.apagar(employee2.getId()));

        assertDoesNotThrow(() -> service.apagar(employee.getId()));

    }
}
