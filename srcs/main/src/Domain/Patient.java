package Domain;

import java.util.Objects;

public class Patient extends Entity {
    private String surname;
    private String firstname;
    private int age;

    public Patient(int id, String surname, String firstname, int age) {
        super(id);
        this.surname = surname;
        this.firstname = firstname;
        this.age = age;
    }

    public int getId() {
        return super.getId();
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public int getAge() {
        return age;
    }

    public void setSurname(String nume) {
        this.surname = nume;
    }

    public void setFirstname(String prenume) {
        this.firstname = prenume;
    }

    public void setAge(int varsta) {
        this.age = varsta;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "ID=" + getId() +
                ", Nume='" + surname + '\'' +
                ", Prenume='" + firstname + '\'' +
                ", Varsta=" + age +
                '}';
    }

    public String toCSVString() {
        return getId() + ";" + surname + ";" + firstname + ";" + age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return age == patient.age && Objects.equals(surname, patient.surname) && Objects.equals(firstname, patient.firstname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, firstname, age);
    }
}
