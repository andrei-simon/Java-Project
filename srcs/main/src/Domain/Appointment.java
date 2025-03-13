package Domain;

import java.util.Date;
import java.util.Objects;

public class Appointment extends Entity {
    private Patient patient;
    private Date date;
    private String purpose;

    public Appointment(int id, Patient patient, Date date, String purpose) {
        super(id);
        this.patient = patient;
        this.date = date;
        this.purpose = purpose;
    }

    public int getID() {
        return super.getId();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public java.sql.Date getSQLDate() {
        return new java.sql.Date(date.getTime());
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "Programare{" +
                "ID=" + getID() +
                ", "+ patient.toString() +
                ", Data=" + date +
                ", Scop='" + purpose + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(patient, that.patient) && Objects.equals(date, that.date) && Objects.equals(purpose, that.purpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patient, date, purpose);
    }
}
