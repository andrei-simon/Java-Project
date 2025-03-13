package Tests;

import Domain.Appointment;
import Domain.AppointmentConverter;
import Domain.Patient;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentConverterTest {
    AppointmentConverter appointmentConverter = new AppointmentConverter();
    Patient patient = new Patient(1, "Popescu", "Ion", 23);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    String appointmentString = "1;1;Popescu;Ion;23;12/12/2020 12:00;Consultatie";
    Appointment appointment = new Appointment(1, patient, dateFormat.parse("12/12/2020 12:00"), "Consultatie");

    AppointmentConverterTest() throws ParseException {
    }

    @Test
    void testToString() {
        assertEquals(appointmentString, appointmentConverter.toString(appointment));
    }

    @Test
    void fromString() {
        assertEquals(appointment, appointmentConverter.fromString(appointmentString));
    }
}