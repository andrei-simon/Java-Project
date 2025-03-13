package Tests;

import Domain.Patient;
import Repository.BinaryFileRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class BinaryFileRepoTest {
    private String fileName = "testbinaryfile.bin";
    private BinaryFileRepo<Patient> repo = new BinaryFileRepo<>(fileName);
    private Collection<Patient> entities;

    @BeforeEach
    void clearFile() {
        try {
            repo.remove(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void add() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        try {
            repo.add(patient);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            this.entities = (Collection<Patient>) ois.readObject();
            assertTrue(entities.contains(patient));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void remove() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        try {
            repo.add(patient);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            repo.remove(1);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            this.entities = (Collection<Patient>) ois.readObject();
            assertFalse(entities.contains(patient));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void update() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        try {
            repo.add(patient);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        Patient patient2 = new Patient(1, "Popescu", "Ion", 24);
        try {
            repo.update(patient2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            this.entities = (Collection<Patient>) ois.readObject();
            assertTrue(entities.contains(patient2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getAll() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        try {
            repo.add(patient);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            this.entities = repo.getAll();
            assertTrue(entities.contains(patient));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void get() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        try {
            repo.add(patient);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            Patient patient2 = repo.get(1);
            assertEquals(patient, patient2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}