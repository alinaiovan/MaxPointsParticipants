import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AbstractXMLRepository;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddAssignmentTest {
    private TemaXMLRepo assignmentFileRepository;
    private TemaValidator temaValidator;
    private Service service;

    @BeforeAll
    public static void createXML() {
        File xml = new File("fisiere/temeTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setup() {
        this.assignmentFileRepository = new TemaXMLRepo("fisiere/temeTest.xml");
        this.temaValidator = new TemaValidator();
        this.service = new Service(null, null, assignmentFileRepository, temaValidator, null, null);
    }

    @AfterAll
    public static void removeXML() {
        new File("fisiere/temeTest.xml").delete();
    }

    @Test
    public void validAssignment_test() {
        Tema newTema = new Tema("1", "SSVV Lab2 bbt", 4, 2);
        this.service.addTema(newTema);
        assertEquals(this.service.getAllTeme().iterator().next(), newTema);
    }

    @Test
    public void emptyAssignmentId(){
        Tema newTema = new Tema("", "SSVV Lab2 bbt", 4, 2);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }
}
