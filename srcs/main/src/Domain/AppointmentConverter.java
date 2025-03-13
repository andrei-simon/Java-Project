package Domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentConverter extends EntityConverter<Appointment> {
    @Override
    public String toString(Appointment entity) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateStr = dateFormat.format(entity.getDate());
        return entity.getId() + ";" + entity.getPatient().getId() + ";" + entity.getPatient().getSurname() + ";" + entity.getPatient().getFirstname()
                + ";" + entity.getPatient().getAge() + ";" + dateStr + ";" + entity.getPurpose();
    }

    @Override
    public Appointment fromString(String string) {
        try {
            String[] parts = string.split(";");
            int id = Integer.parseInt(parts[0]);
            int patientId = Integer.parseInt(parts[1]);
            String surname = parts[2];
            String firstname = parts[3];
            int age = Integer.parseInt(parts[4]);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = dateFormat.parse(parts[5]);
            String purpose = parts[6];

            Patient patient = new Patient(patientId, surname, firstname, age);
            return new Appointment(id, patient, date, purpose);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
