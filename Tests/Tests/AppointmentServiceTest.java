package Tests;

import Domain.Appointment;
import Domain.Patient;
import Repository.MemoryRepo;
import Service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {
    private AppointmentService appointment_service;
    private MemoryRepo<Appointment> appointment_repo;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private List<Patient> patients = new java.util.ArrayList<>();

    @BeforeEach
    void setUp() throws ParseException {
        appointment_repo = new MemoryRepo<>();
        patients.add(new Patient(1, "Popescu", "Ion", 23));
        patients.add(new Patient(2, "Ionescu", "Maria", 45));
        patients.add(new Patient(3, "Popa", "Vasile", 34));
        appointment_repo.add(new Appointment(1, patients.get(1), dateFormat.parse("23/05/2024 10:00"), "Consultatie"));
        appointment_service = new AppointmentService(appointment_repo);
    }

    @Test
    void updateAppointmentPatient() {
        try {
            appointment_service.addAppointment(2, 2, dateFormat.parse("15/07/2024 15:00"), "Consultatie", patients);
            appointment_service.updateAppointmentPatient(2, "Vlad", "Ion", 23);
            assertEquals("Vlad", appointment_service.getAllAppointments().iterator().next().getPatient().getSurname());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void deleteAppointmentPatient() {
        try {
            appointment_service.addAppointment(2, 2, dateFormat.parse("10/09/2024 18:00"), "Consultatie", patients);
            appointment_service.deleteAppointmentPatient(2);
            assertEquals(1, appointment_repo.getAll().spliterator().getExactSizeIfKnown());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void addAppointment() {
        try {
            appointment_service.addAppointment(2, 1, dateFormat.parse("03/09/2023 12:00"), "Consultatie", patients);
            appointment_service.addAppointment(3, 2, dateFormat.parse("05/05/2023 13:30"), "Consultatie", patients);
            appointment_service.addAppointment(4, 3, dateFormat.parse("30/07/2023 16:45"), "Consultatie", patients);
            assertEquals(4, appointment_service.getAllAppointments().spliterator().getExactSizeIfKnown());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void updateAppointment() {
        try {
            appointment_service.addAppointment(2, 1, dateFormat.parse("03/09/2023 12:00"), "Consultatie", patients);
            appointment_service.updateAppointment(2, 1, dateFormat.parse("05/05/2023 13:30"), "Consultatie noua", patients);
            assertEquals("Consultatie", appointment_service.getAllAppointments().iterator().next().getPurpose());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void deleteAppointment() {
        try {
            appointment_service.addAppointment(2, 1, dateFormat.parse("03/09/2023 12:00"), "Consultatie", patients);
            appointment_service.addAppointment(3, 2, dateFormat.parse("05/05/2023 13:30"), "Consultatie", patients);
            appointment_service.addAppointment(4, 3, dateFormat.parse("30/07/2023 16:45"), "Consultatie", patients);
            appointment_service.deleteAppointment(2);
            assertEquals(3, appointment_service.getAllAppointments().spliterator().getExactSizeIfKnown());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getAllAppointments() {
        try {
            appointment_service.addAppointment(2, 1, dateFormat.parse("03/09/2023 12:00"), "Consultatie", patients);
            appointment_service.addAppointment(3, 2, dateFormat.parse("05/05/2023 13:30"), "Consultatie", patients);
            appointment_service.addAppointment(4, 3, dateFormat.parse("30/07/2023 16:45"), "Consultatie", patients);
            Collection<Appointment> appointments = appointment_service.getAllAppointments();
            for(Appointment a : appointments) {
                assertEquals(true, a.getPurpose() == "Consultatie" || a.getPatient().getFirstname() == "Maria" || a.getPatient().getAge() == 34);
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}