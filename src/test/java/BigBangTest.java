import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BigBangTest {
    private StudentXMLRepo studentFileRepository;
    private StudentValidator studentValidator;
    private TemaXMLRepo assignmentFileRepository;
    private TemaValidator assignmentValidator;
    private NotaXMLRepo gradeFileRepository;
    private NotaValidator gradeValidator;
    private NotaValidator gradeValidatorBB;
    private StudentXMLRepo sr;
    private NotaXMLRepo nr;
    private TemaXMLRepo tr;
    private Service service;
    private Service serviceBB;
    private static final String STUDENTS_XML  = "fisiere/studentiTest.xml";
    private static final String ASSIGNMENTS_XML  = "fisiere/temeTest.xml";
    private static final String GRADES_XML  = "fisiere/noteTest.xml";
    private static final String STUDENTS_XML_BB  = "fisiere/studentiTestBB.xml";
    private static final String ASSIGNMENTS_XML_BB  = "fisiere/temeTestBB.xml";
    private static final String GRADES_XML_BB  = "fisiere/noteTestBB.xml";



    @BeforeAll
    public static void createXML() {
        File xmlStudents = new File(STUDENTS_XML);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlStudents))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File xmlAssignments = new File(ASSIGNMENTS_XML);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlAssignments))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File xmlGrades = new File(GRADES_XML);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlGrades))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File xmlStudentsBB = new File(STUDENTS_XML_BB);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlStudentsBB))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File xmlAssignmentsBB = new File(ASSIGNMENTS_XML_BB);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlAssignmentsBB))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File xmlGradesBB = new File(GRADES_XML_BB);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlGradesBB))) {
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
        this.studentFileRepository = new StudentXMLRepo(STUDENTS_XML);
        this.studentValidator = new StudentValidator();
        this.assignmentFileRepository = new TemaXMLRepo(ASSIGNMENTS_XML);
        this.assignmentValidator = new TemaValidator();
        this.gradeFileRepository = new NotaXMLRepo(GRADES_XML);
        this.gradeValidator = new NotaValidator(this.studentFileRepository, this.assignmentFileRepository);
        this.sr = new StudentXMLRepo(STUDENTS_XML_BB);
        this.tr = new TemaXMLRepo(ASSIGNMENTS_XML_BB);
        this.nr =  new NotaXMLRepo(GRADES_XML_BB);
        this.gradeValidatorBB = new NotaValidator(sr, tr);
        this.service = new Service(this.studentFileRepository, this.studentValidator, this.assignmentFileRepository, this.assignmentValidator, this.gradeFileRepository, this.gradeValidator);
        this.serviceBB = new Service(sr, this.studentValidator, tr, this.assignmentValidator,nr, this.gradeValidatorBB);
    }

    @AfterAll
    public static void removeXML() {
        new File(STUDENTS_XML).delete();
        new File(ASSIGNMENTS_XML).delete();
        new File(GRADES_XML).delete();
        new File(STUDENTS_XML_BB).delete();
        new File(ASSIGNMENTS_XML_BB).delete();
        new File(GRADES_XML_BB).delete();
    }

    @Test
    public void validStudent_test() {
        Student newStudent = new Student("1234", "Alin Bogdan", 123, "alin@yahoo.com");
        this.service.addStudent(newStudent);
        assertEquals(this.service.getAllStudenti().iterator().next(), newStudent);
    }

    @Test
    public void validAssignment_test() {
        Tema newTema = new Tema("1", "SSVV Lab3", 4, 2);
        this.service.addTema(newTema);
        assertEquals(this.service.getAllTeme().iterator().next(), newTema);
    }

    @Test
    public void validGrade_test() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String dateString = "27/10/2023";
        LocalDate date = LocalDate.parse(dateString, formatter);

        Nota newNota = new Nota("1", "1234", "1", 8.3, date);
        this.service.addNota(newNota, "Ok");
        assertEquals(this.service.getAllNote().iterator().next(), newNota);
    }

    @Test
    public void validStudentAssignmentGrade_test() {
        Student newStudent = new Student("1", "Alin Bogdan", 123, "alin@yahoo.com");
        this.serviceBB.addStudent(newStudent);
        assertEquals(this.serviceBB.getAllStudenti().iterator().next(), newStudent);

        Tema newTema = new Tema("1", "SSVV Lab3", 4, 2);
        this.serviceBB.addTema(newTema);
        assertEquals(this.serviceBB.getAllTeme().iterator().next(), newTema);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String dateString = "27/10/2023";
        LocalDate date = LocalDate.parse(dateString, formatter);

        Nota newNota = new Nota("1", newStudent.getID(), newTema.getID(), 8.3, date);
        this.serviceBB.addNota(newNota, "Ok");
        assertEquals(this.serviceBB.getAllNote().iterator().next(), newNota);
    }

}
