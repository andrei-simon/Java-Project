package Tests;

import Domain.Patient;
import Repository.MemoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryRepoTest {
    private MemoryRepo<Patient> repo;
    @BeforeEach
    void setUp() {
        repo = new MemoryRepo<>();
    }

    @Test
    void add() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        try {
            repo.add(patient);
            assertEquals(repo.get(1), patient);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void remove() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        Patient patient2 = new Patient(2, "Popescu", "Ion", 30);
        try {
            repo.add(patient);
            repo.add(patient2);
            repo.remove(1);
            assertEquals(repo.getAll().size(), 1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void getAll() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        try {
            repo.add(patient);
            assertEquals(repo.getAll().size(), 1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void get() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        try {
            repo.add(patient);
            assertEquals(repo.get(1), patient);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void update() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        try {
            repo.add(patient);
            Patient patient2 = new Patient(1, "Popescu", "Ion", 24);
            repo.update(patient2);
            assertEquals(repo.get(1).getAge(), 24);
        } catch (Exception e) {
            fail();
        }
    }
}