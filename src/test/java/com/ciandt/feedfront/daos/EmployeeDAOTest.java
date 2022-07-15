package com.ciandt.feedfront.daos;
//
//import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.dao.EmployeeDAO;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.interfaces.DAO;
import com.ciandt.feedfront.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDAOTest {
    private Employee employee;
    private DAO<Employee> dao = new EmployeeDAO();

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
    public void salvar() {
        assertDoesNotThrow(() -> dao.salvar(employee));
        assertDoesNotThrow(() -> dao.salvar(new Employee("Jose", "Silveira", "j.silveira@email.com")));
    }

    @Test
    public void atualizarDados() {
        String novoNome = "bruno";
        String novoEmail = "b.silveira@email.com";
        assertDoesNotThrow(() -> dao.salvar(employee));

        try {
            employee.setNome(novoNome);
            employee.setEmail(novoEmail);
        } catch (ComprimentoInvalidoException ignored) {
        }

        Employee employeeSalvo = assertDoesNotThrow(() -> dao.buscar(employee.getId()));

        assertNotEquals(employeeSalvo.getNome(), novoNome);
        assertNotEquals(employeeSalvo.getEmail(), novoEmail);

        Employee employeAtualizado = assertDoesNotThrow(() -> dao.salvar(employee));

        assertEquals(employeAtualizado, employee);
    }

    @Test
    public void listar() {
        List<Employee> result = assertDoesNotThrow(dao::listar);

        assertTrue(result.isEmpty());

        assertDoesNotThrow(() -> dao.salvar(employee));
        assertDoesNotThrow(() -> dao.salvar(new Employee("Jose", "Silveira", "j.silveira@email.com")));

        result = assertDoesNotThrow(dao::listar);

        assertFalse(result.isEmpty());
    }

    @Test
    public void buscar() {
        String uuid = UUID.randomUUID().toString();
        assertThrows(IOException.class, () -> dao.buscar(uuid));

        assertDoesNotThrow(() -> dao.salvar(employee));
        Employee employeSalvo = assertDoesNotThrow(() -> dao.buscar(employee.getId()));

        assertEquals(employeSalvo, employee);
    }

    @Test
    public void apagar() {
        assertDoesNotThrow(() -> dao.salvar(employee));
        assertDoesNotThrow(() -> dao.apagar(employee.getId()));

        assertThrows(IOException.class, () -> dao.buscar(employee.getId()));
    }
}
