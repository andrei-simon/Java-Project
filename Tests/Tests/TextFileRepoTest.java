package Tests;

import Domain.Patient;
import Domain.PatientConverter;
import Repository.RepoException;
import Repository.TextFileRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TextFileRepoTest {
    private PatientConverter converter = new PatientConverter();
    private String fileName = "Tests/testtextfile.txt";
    private TextFileRepo<Patient> repo = new TextFileRepo<>(fileName,converter);
    private Collection<Patient> entities = new ArrayList<>();


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
            fail();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line = br.readLine();
            while (line != null) {
                entities.add(converter.fromString(line));
                line = br.readLine();
            }
        } catch (IOException e) {
            fail();
        }
        assertTrue(entities.contains(patient));
    }

    @Test
    void remove() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        try {
            repo.add(patient);
            repo.remove(1);
        } catch (Exception e) {
            fail();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line = br.readLine();
            while (line != null) {
                entities.add(converter.fromString(line));
                line = br.readLine();
            }
        } catch (IOException e) {
            fail();
        }
        assertFalse(entities.contains(patient));
    }

    @Test
    void update() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        Patient patient2 = new Patient(1, "Ionescu", "Maria", 45);
        try {
            repo.add(patient);
            repo.update(patient2);
        } catch (Exception e) {
            fail();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line = br.readLine();
            while (line != null) {
                entities.add(converter.fromString(line));
                line = br.readLine();
            }
        } catch (IOException e) {
            fail();
        }
        assertTrue(entities.contains(patient2));
        assertFalse(entities.contains(patient));
    }

    @Test
    void getAll() {
        Patient patient = new Patient(1, "Popescu", "Ion", 23);
        try {
            repo.add(patient);
        } catch (Exception e) {
            fail();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line = br.readLine();
            while (line != null) {
                entities.add(converter.fromString(line));
                line = br.readLine();
            }
        } catch (IOException e) {
            fail();
        }
        assertTrue(entities.contains(patient));
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
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line = br.readLine();
            while (line != null) {
                entities.add(converter.fromString(line));
                line = br.readLine();
            }
        } catch (IOException e) {
            fail();
        }
        assertTrue(entities.contains(patient));
    }
}