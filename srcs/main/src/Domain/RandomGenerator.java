package Domain;

import Repository.SQLAppointmentRepo;
import Repository.SQLPatientRepo;
import Service.AppointmentService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {
    String surnnames[] = {"Popescu", "Ionescu", "Georgescu", "Vasilescu", "Dumitrescu", "Stanescu", "Stoica", "Stefanescu", "Gheorghe", "Constantinescu", "Simon", "Ardelean", "Anghelescu", "Bujor", "baciu", "Chirila", "Cornea", "Ciortea", "Cantemir", "Dragomir", "Iancu", "Ispas", "Logojan", "Olaru", "Palade", "Racovita", "Radu", "Rusu", "Sasu", "Serban", "Stoica", "Trif", "Ungureanu", "Rieger"};
    String firstnames[] = {"Ion", "Maria", "Mihai", "Andrei", "Cristina", "Andreea", "Alex", "Alina", "Diana", "Gabriel", "George", "Vasile", "Vlad", "Victor", "Valeria", "Valentin", "Vladimir", "Carla", "Timeea", "Alexandra", "Camelia", "Elena", "Florin", "Cozmin", "Eduard", "Dorin", "Riana"};
    String purposes[] = {"Control", "Operatie", "Plomba", "Extractie", "Detartraj", "Albire", "Proteza", "Aparat dentar", "Tratament canal", "Chirurgie"};
    static SQLPatientRepo patient_repo = new SQLPatientRepo();
    static SQLAppointmentRepo appointment_repo = new SQLAppointmentRepo();

    public static void generatePatients() {
        RandomGenerator generator = new RandomGenerator();
        for(int i = 0; i < 100; i++) {
            int id = i + 1;
            String surname = generator.surnnames[(int)(Math.random() * generator.surnnames.length)];
            String firstname = generator.firstnames[(int)(Math.random() * generator.firstnames.length)];
            int age = new java.util.Random().nextInt(56) + 24;
            Patient patient = new Patient(id, surname, firstname, age);
            patient_repo.add(patient);
        }
    }

    public static void generateAppointments() {
        RandomGenerator generator = new RandomGenerator();
        for(int i = 0; i < 100; i++) {
            int id = i + 1;
            int patient_id = new java.util.Random().nextInt(99) + 1;
            Patient patient = patient_repo.get(patient_id);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try{
                Date d1 = dateFormat.parse("01/01/2024 00:00");
                Date d2 = dateFormat.parse("31/12/2024 23:59");
                Date date = new Date(ThreadLocalRandom.current().nextLong(d1.getTime(), d2.getTime()));
                String purpose = generator.purposes[(int)(Math.random() * generator.purposes.length)];
                Appointment appointment = new Appointment(id, patient, date, purpose);
                appointment_repo.add(appointment);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        AppointmentService appointmentService = new AppointmentService(appointment_repo);
        Map<Integer, Long> appointments = appointmentService.getAppointmentsCountByMonth();
        for (Map.Entry<Integer, Long> entry : appointments.entrySet()) {
            System.out.println("Month " + entry.getKey() + " : " + entry.getValue());
        }

    }
}
