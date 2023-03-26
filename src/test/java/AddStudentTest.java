import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.*;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

public class AddStudentTest {
    StudentXMLRepo repo;
    TemaXMLRepo temaRepo;
    Service service;

    @BeforeEach
    void setUp(){
        repo = new StudentXMLRepo("fisiere/Studenti.xml");
        temaRepo = new TemaXMLRepo("fisiere/Teme.xml");
        service = new Service(repo, new StudentValidator(), temaRepo, new TemaValidator(), new NotaXMLRepo("fisiere/Note.xml"), new NotaValidator(repo, temaRepo));
    }


@Test
    void testValidId(){
        Student student = new Student("2222", "gigel", 222, "gigimail" );
    var result  = repo.save(student);
    assertNull(result);
    repo.delete(student.getID());
}

    @Test
    void testInvalidId(){
        Student student = new Student("", "ana", 222, "gigimail" );
        //var result  = repo.save(student);
        Exception exception = assertThrows(ValidationException.class, () -> {service.addStudent(student);});
        String expectedMessage = "Id incorect!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        //assertThrowsExactly(ValidationException, result);
    }

}
