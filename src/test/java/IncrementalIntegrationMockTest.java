import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IncrementalIntegrationMockTest {

    @Mock
    private StudentValidator studentValidator;

    @Mock
    private TemaValidator temaValidator;

    @Mock
    private StudentXMLRepo studentXMLRepository;

    @Mock
    private TemaXMLRepo temaXMLRepository;

    @Mock
    private NotaValidator notaValidator;

    @Mock
    private NotaXMLRepo notaXMLRepository;

    private Service service;

    @BeforeEach
    public void setup() {

        studentValidator = mock(StudentValidator.class);
        temaValidator = mock(TemaValidator.class);
        notaValidator = mock(NotaValidator.class);
        temaXMLRepository = mock(TemaXMLRepo.class);
        studentXMLRepository = mock(StudentXMLRepo.class);
        notaXMLRepository = mock(NotaXMLRepo.class);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }


    @Test
    public void testAddStudent() {
        Student student = new Student("", "ana", 931, "ana@gmail.com");

        try {
            doThrow(new ValidationException("Nume incorect!")).when(studentValidator).validate(student);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        try {
            Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testAddStudentAndAssignment() {
        Student student = new Student("222", "ana", 931, "ana@gmail.com");
        Tema tema = new Tema("223", "", 1, 1);

        try {
            doNothing().when(studentValidator).validate(student);
            when(studentXMLRepository.save(student)).thenReturn(null);
            doThrow(new ValidationException("Descriere invalida!")).when(temaValidator).validate(tema);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        try {
            Student s1_test = service.addStudent(student);
            Assertions.assertNull(s1_test);
            Assertions.assertThrows(ValidationException.class, () -> service.addTema(tema));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testAddStudentAndAssignmentAndGrade() {
        Student student = new Student("222", "ana", 931, "ana@gmail.com");
        Tema tema = new Tema("222", "a", 1, 2);
        Nota nota = new Nota("222", "222", "222", 10, LocalDate.now());


        try {
            doNothing().when(studentValidator).validate(student);
            when(studentXMLRepository.save(student)).thenReturn(null);

            doNothing().when(temaValidator).validate(tema);
            when(temaXMLRepository.save(tema)).thenReturn(null);

            when(studentXMLRepository.findOne(nota.getIdStudent())).thenReturn(student);
            when(temaXMLRepository.findOne(nota.getIdTema())).thenReturn(tema);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        try {
            Student s1_test = service.addStudent(student);
            Assertions.assertNull(s1_test);
            Tema tema1_test = service.addTema(tema);
            Assertions.assertNull(tema1_test);

            Nota nota1_test = service.addNota(nota, "ok");
            Assertions.assertEquals(1.0, nota.getNota());
            Assertions.assertNull(nota1_test);

        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


}
