package Tests;

import Domain.Patient;
import Domain.PatientConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PatientConverterTest {
    private PatientConverter patientConverter = new PatientConverter();
    String patientString = "1;Popescu;Ion;23";
    Patient patient = new Patient(1, "Popescu", "Ion", 23);
    @Test
    void testToString() {
        assertEquals(patientString, patientConverter.toString(patient));
    }

    @Test
    void fromString() {
        assertEquals(patient, patientConverter.fromString(patientString));
    }
}