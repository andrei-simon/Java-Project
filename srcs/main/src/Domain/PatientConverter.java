package Domain;

public class PatientConverter extends EntityConverter<Patient> {
    @Override
    public String toString(Patient patient) {
        return patient.getId() + ";" + patient.getSurname() + ";" + patient.getFirstname() + ";" + patient.getAge();
    }

    @Override
    public Patient fromString(String string) {
        String[] tokens = string.split(";");
        return new Patient(Integer.parseInt(tokens[0]), tokens[1], tokens[2], Integer.parseInt(tokens[3]));
    }
}
