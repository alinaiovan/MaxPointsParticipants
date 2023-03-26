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
    public void TestAddStudent_ValidStudent_StudentAddedCorrectly() {
        Student newStudent = new Student("1111", "a b", 999, "aa@yahoo.com");
        this.service.addStudent(newStudent);
        assertEquals(this.service.getAllStudenti().iterator().next(), newStudent);
    }

    @Test
    public void TestAddStudent_NegativeStudentGroup() {
        Student newStudent = new Student("1111", "a b", -5, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_StudentGroupTooLarge() {
        Student newStudent = new Student("1111", "a b", 1000, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_EmptyStudentName() {
        Student newStudent = new Student("1111", "", 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_NullStudentName() {
        Student newStudent = new Student("1111", null, 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_StudentNameContainsIllegalSymbols() {
        Student newStudent = new Student("1111", "aa11 bb", 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_StudentNameIsJustOneWord() {
        Student newStudent = new Student("1111", "aa", 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_EmptyStudentId() {
        Student newStudent = new Student("", "aa bb", 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_NullStudentId() {
        Student newStudent = new Student(null, "aa bb", 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_EmptyStudentEmail() {
        Student newStudent = new Student("1111", "aa bb", 999, "");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_NullStudentEmail() {
        Student newStudent = new Student("1111", "aa bb", 999, null);
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_WrongStudentEmailLocalPart() {
        Student newStudent = new Student("1111", "aa bb", 999, "@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_WrongStudentEmailDomain() {
        Student newStudent = new Student("1111", "aa bb", 999, "aa@yahoo.con");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_DigitInStudentEmailDomain() {
        Student newStudent = new Student("1111", "aa bb", 999, "aa@yahoo1.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    public void TestAddStudent_NoAtSymbolInStudentEmail() {
        Student newStudent = new Student("1111", "aa bb", 999, "aayahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }
}
