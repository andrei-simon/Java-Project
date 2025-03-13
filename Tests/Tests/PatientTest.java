package Tests;

import Domain.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PatientTest {
    private Patient patient = new Patient(1, "Popescu", "Ion", 30);

    @Test
    void getId() {
        assertEquals(1, patient.getId());
    }

    @Test
    void getSurname() {
        assertEquals("Popescu", patient.getSurname());
    }

    @Test
    void getFirstname() {
        assertEquals("Ion", patient.getFirstname());
    }

    @Test
    void getAge() {
        assertEquals(30, patient.getAge());
    }

    @Test
    void setSurname() {
        patient.setSurname("Ionescu");
        assertEquals("Ionescu", patient.getSurname());
    }

    @Test
    void setFirstname() {
        patient.setFirstname("Vasile");
        assertEquals("Vasile", patient.getFirstname());
    }

    @Test
    void setAge() {
        patient.setAge(40);
        assertEquals(40, patient.getAge());
    }

    @Test
    void testToString() {
        assertEquals("Pacient{ID=1, Nume='Popescu', Prenume='Ion', Varsta=30}", patient.toString());
    }
}