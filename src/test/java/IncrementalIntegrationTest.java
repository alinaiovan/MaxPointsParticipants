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

public class IncrementalIntegrationTest {

    private StudentXMLRepo sr_S;
    private StudentValidator studentValidator;
    private TemaXMLRepo tr_S;
    private TemaValidator assignmentValidator;
    private NotaXMLRepo nr_S;
    private NotaValidator gradeValidator_S;
    private Service service_S;
    private static final String STUDENTS_XML_S = "fisiere/studentiTest.xml";
    private static final String ASSIGNMENTS_XML_S = "fisiere/temeTest.xml";
    private static final String GRADES_XML_S = "fisiere/noteTest.xml";

    private StudentXMLRepo sr_SA;
    private NotaXMLRepo nr_SA;
    private TemaXMLRepo tr_SA;
    private Service service_SA;
    private NotaValidator gradeValidator_SA;
    private static final String STUDENTS_XML_SA = "fisiere/studentiTestSA.xml";
    private static final String ASSIGNMENTS_XML_SA = "fisiere/temeTestSA.xml";
    private static final String GRADES_XML_SA = "fisiere/noteTestSA.xml";

    private StudentXMLRepo sr_SAG;
    private NotaXMLRepo nr_SAG;
    private TemaXMLRepo tr_SAG;
    private Service service_SAG;
    private NotaValidator gradeValidator_SAG;
    private static final String STUDENTS_XML_SAG = "fisiere/studentiTestSAG.xml";
    private static final String ASSIGNMENTS_XML_SAG = "fisiere/temeTestSAG.xml";
    private static final String GRADES_XML_SAG = "fisiere/noteTestSAG.xml";

    @BeforeAll
    public static void createXML() {
        // S
        File xmlStudents = new File(STUDENTS_XML_S);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlStudents))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File xmlAssignments = new File(ASSIGNMENTS_XML_S);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlAssignments))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File xmlGrades = new File(GRADES_XML_S);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlGrades))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // SA
        File xmlStudentsSA = new File(STUDENTS_XML_SA);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlStudentsSA))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File xmlAssignmentsSA = new File(ASSIGNMENTS_XML_SA);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlAssignmentsSA))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File xmlGradesSA = new File(GRADES_XML_SA);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlGradesSA))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // SAG
        File xmlStudentsSAG = new File(STUDENTS_XML_SAG);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlStudentsSAG))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File xmlAssignmentsSAG = new File(ASSIGNMENTS_XML_SAG);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlAssignmentsSAG))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<inbox>" +

                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File xmlGradesSAG = new File(GRADES_XML_SAG);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlGradesSAG))) {
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
        this.sr_S = new StudentXMLRepo(STUDENTS_XML_S);
        this.studentValidator = new StudentValidator();
        this.tr_S = new TemaXMLRepo(ASSIGNMENTS_XML_S);
        this.assignmentValidator = new TemaValidator();
        this.nr_S = new NotaXMLRepo(GRADES_XML_S);
        this.sr_SA = new StudentXMLRepo(STUDENTS_XML_SA);
        this.tr_SA = new TemaXMLRepo(ASSIGNMENTS_XML_SA);
        this.nr_SA = new NotaXMLRepo(GRADES_XML_SA);
        this.sr_SAG = new StudentXMLRepo(STUDENTS_XML_SAG);
        this.tr_SAG = new TemaXMLRepo(ASSIGNMENTS_XML_SAG);
        this.nr_SAG = new NotaXMLRepo(GRADES_XML_SAG);
        this.gradeValidator_S = new NotaValidator(this.sr_S, this.tr_S);
        this.gradeValidator_SA = new NotaValidator(sr_SA, tr_SA);
        this.gradeValidator_SAG = new NotaValidator(sr_SAG, tr_SAG);
        this.service_S = new Service(this.sr_S, this.studentValidator, this.tr_S, this.assignmentValidator, this.nr_S, this.gradeValidator_S);
        this.service_SAG = new Service(sr_SAG, this.studentValidator, tr_SAG, this.assignmentValidator, nr_SAG, this.gradeValidator_SAG);
        this.service_SA = new Service(sr_SA, this.studentValidator, tr_SA, this.assignmentValidator, nr_SA, this.gradeValidator_SA);

    }

    @AfterAll
    public static void removeXML() {
        new File(STUDENTS_XML_S).delete();
        new File(ASSIGNMENTS_XML_S).delete();
        new File(GRADES_XML_S).delete();
        new File(STUDENTS_XML_SA).delete();
        new File(ASSIGNMENTS_XML_SA).delete();
        new File(GRADES_XML_SA).delete();
        new File(STUDENTS_XML_SAG).delete();
        new File(ASSIGNMENTS_XML_SAG).delete();
        new File(GRADES_XML_SAG).delete();
    }

    @Test
    public void validStudent_test() {
        Student newStudent = new Student("1234", "Alin Bogdan", 123, "alin@yahoo.com");
        this.service_S.addStudent(newStudent);
        assertEquals(this.service_S.getAllStudenti().iterator().next(), newStudent);
    }

    @Test
    public void validStudentAssignment_test() {
        Student newStudent = new Student("1234", "Alin Bogdan", 123, "alin@yahoo.com");
        this.service_SA.addStudent(newStudent);
        Tema newTema = new Tema("1", "SSVV Lab3", 4, 2);
        this.service_SA.addTema(newTema);
        assertEquals(this.service_SA.getAllTeme().iterator().next(), newTema);
    }

    @Test
    public void validStudentAssignmentGrade_test() {
        Student newStudent = new Student("1", "Alin Bogdan", 123, "alin@yahoo.com");
        this.service_SAG.addStudent(newStudent);
        assertEquals(this.service_SAG.getAllStudenti().iterator().next(), newStudent);

        Tema newTema = new Tema("1", "SSVV Lab3", 4, 2);
        this.service_SAG.addTema(newTema);
        assertEquals(this.service_SAG.getAllTeme().iterator().next(), newTema);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String dateString = "27/10/2023";
        LocalDate date = LocalDate.parse(dateString, formatter);

        Nota newNota = new Nota("1", newStudent.getID(), newTema.getID(), 8.3, date);
        this.service_SAG.addNota(newNota, "Ok");
        assertEquals(this.service_SAG.getAllNote().iterator().next(), newNota);
    }


}
