package Tests;

import Domain.Patient;
import Repository.MemoryRepo;
import Service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class PatientServiceTest {
    private PatientService patient_service;
    private MemoryRepo<Patient> patient_repo;

    @BeforeEach
    void setUp() {
        patient_repo = new MemoryRepo<>();
        patient_service = new PatientService(patient_repo);
    }
    @Test
    void addPatient() {
        try {
            patient_service.addPatient(1, "Popescu", "Ion", 23);
            patient_service.addPatient(2, "Ionescu", "Maria", 45);
            patient_service.addPatient(3, "Popa", "Vasile", 34);
            assertEquals(3, patient_service.getAllPatients().spliterator().getExactSizeIfKnown());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void updatePatient() {
        try {
            patient_service.addPatient(1, "Popescu", "Ion", 23);
            patient_service.updatePatient(1, "Ionescu", "Maria", 46);
            assertEquals("Maria", patient_service.getAllPatients().iterator().next().getFirstname());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void deletePatient() {
        try {
            patient_service.addPatient(1, "Popescu", "Ion", 23);
            patient_service.addPatient(2, "Ionescu", "Maria", 45);
            patient_service.addPatient(3, "Popa", "Vasile", 34);
            patient_service.deletePatient(2);
            assertEquals(2, patient_service.getAllPatients().spliterator().getExactSizeIfKnown());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getAllPatients() {
        try {
            patient_service.addPatient(1, "Popescu", "Ion", 23);
            patient_service.addPatient(2, "Ionescu", "Maria", 45);
            patient_service.addPatient(3, "Popa", "Vasile", 34);
            Collection<Patient> patients = (Collection<Patient>) patient_service.getAllPatients();
            for(Patient p : patients) {
                assertEquals(true, p.getSurname() == "Popescu" || p.getFirstname() == "Maria" || p.getAge() == 34);
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}