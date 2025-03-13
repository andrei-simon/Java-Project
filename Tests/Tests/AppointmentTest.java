package Tests;

import Domain.Appointment;
import Domain.Patient;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
class AppointmentTest {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private Patient patient = new Patient(1, "Popescu", "Ion", 23);
    private Appointment appointment = new Appointment(1, patient, dateFormat.parse("12/12/2020 12:00"), "Consultatie");

    AppointmentTest() throws ParseException {
    }

    @Test
    void getID() {
        assertEquals(1, appointment.getID());
    }

    @Test
    void getPatient() {
        assertEquals(patient, appointment.getPatient());
    }

    @Test
    void setPatient() {
        Patient patient2 = new Patient(2, "Ionescu", "Maria", 34);
        appointment.setPatient(patient2);
        assertEquals(patient2, appointment.getPatient());
    }

    @Test
    void getDate() throws ParseException {
        assertEquals(dateFormat.parse("12/12/2020 12:00"), appointment.getDate());
    }

    @Test
    void setDate() {
        try {
            appointment.setDate(dateFormat.parse("13/12/2020 12:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(appointment.getDate(), appointment.getDate());
    }

    @Test
    void getPurpose() {
        assertEquals("Consultatie", appointment.getPurpose());
    }

    @Test
    void setPurpose() {
        appointment.setPurpose("Tratament");
        assertEquals("Tratament", appointment.getPurpose());
    }

    @Test
    void testToString() {
        assertEquals("Programare{ID=1, Pacient{ID=1, Nume='Popescu', Prenume='Ion', Varsta=23}, Data=Sat Dec 12 12:00:00 EET 2020, Scop='Consultatie'}", appointment.toString());
    }

}