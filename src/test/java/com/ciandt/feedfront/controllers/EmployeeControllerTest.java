package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// A função do controller é tratar as solicitações.
// Ser capaz de levar o "pedido" ao "cozinheiro" e me trazer o "prato"
public class EmployeeControllerTest {
    private Employee employee;
    private EmployeeController controller = new EmployeeController();

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
            controller.salvar(employee); // Isso também pode ser considerado teste
        } catch (BusinessException ignored) {
        }
    }

    @Test
    public void listar() {
        Collection<Employee> employees = assertDoesNotThrow(controller::listar);

        assertTrue(employees instanceof List);
    }

    @Test
    public void buscar() {
        String uuid = employee.getId();
        Employee employeeSalvo = assertDoesNotThrow(() -> controller.buscar(uuid));

        assertEquals(employee, employeeSalvo);
    }

    @Test
    public void atualizar() {
        employee.setEmail("joao.silveira@email.com");
        Employee employeeAtualizado = assertDoesNotThrow(() -> controller.atualizar(employee));

        assertEquals(employee, employeeAtualizado);
    }

    @Test
    public void apagar() {
        String uuid = employee.getId();

        assertDoesNotThrow(() -> controller.apagar(uuid));
    }
}
