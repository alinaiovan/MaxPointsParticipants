import domain.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.*;
import service.Service;
import validation.StudentValidator;
import validation.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AddStudentTest {
    private StudentXMLRepo studentFileRepository;
    private StudentValidator studentValidator;
    private Service service;

    @BeforeAll
    public static void createXML() {
        File xml = new File("fisiere/studentiTest.xml");
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
        this.studentFileRepository = new StudentXMLRepo("fisiere/studentiTest.xml");
        this.studentValidator = new StudentValidator();
        this.service = new Service(this.studentFileRepository, this.studentValidator, null, null, null, null);
    }

    @AfterAll
    public static void removeXML() {
        new File("fisiere/studentiTest.xml").delete();
    }

    @Test
    public void validStudent_test() {
        Student newStudent = new Student("1234", "Alin Bogdan", 123, "alin@yahoo.com");
        this.service.addStudent(newStudent);
        assertEquals(this.service.getAllStudenti().iterator().next(), newStudent);
    }

    @Test
    public void negativeStudentGroup() {
        Student newStudent = new Student("1234", "Alin Bogdan", -5, "alin@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void studentGroupTooLarge() {
        Student newStudent = new Student("1234", "Alin Bogdan", 1000, "alin@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void emptyStudentName() {
        Student newStudent = new Student("1234" , "", 123, "alin@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void nullStudentName() {
        Student newStudent = new Student("1234", null, 123, "alin@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void studentNameContainsIllegalSymbols() {
        Student newStudent = new Student("1234", "Alin Bogdan555", 123, "alin@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void studentNameIsJustOneWord() {
        Student newStudent = new Student("1234", "Alin", 123, "alin@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void emptyStudentId() {
        Student newStudent = new Student("", "Alin Bogdan", 123, "alin@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void nullStudentId() {
        Student newStudent = new Student(null, "Alin Bogdan", 123, "alin@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void emptyStudentEmail() {
        Student newStudent = new Student("1234", "Alin Bogdan", 123, "");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void nullStudentEmail() {
        Student newStudent = new Student("1234", "Alin Bogdan", 123, null);
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void wrongStudentEmailLocalPart() {
        Student newStudent = new Student("1234", "Alin Bogdan", 123, "@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void wrongStudentEmailDomain() {
        Student newStudent = new Student("1234", "Alin Bogdan", 123, "alin@yahoo.con");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void digitInStudentEmailDomain() {
        Student newStudent = new Student("1234", "Alin Bogdan", 123, "alin@yahoo1.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void noAtSymbolInStudentEmail() {
        Student newStudent = new Student("1234", "Alin Bogdan", 123, "alinyahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }
}
