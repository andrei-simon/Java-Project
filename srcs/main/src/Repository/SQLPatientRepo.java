package Repository;

import Domain.Patient;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class SQLPatientRepo extends MemoryRepo<Patient> implements AutoCloseable{
    private static final String JDBC_URL =
            "jdbc:sqlite:srcs/main/src/patients.db";

    private Connection conn = null;

    public SQLPatientRepo() {
        openConnection();
        createTable();
        loadData();
    }

    private void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la conectarea cu baza de date", e);
        }
    }

    private void createTable() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS patients(id int primary key, surname varchar(50), firstname varchar(50), age int);");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createTable : " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from patients");
                 ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Patient patient = new Patient(rs.getInt("id"), rs.getString("surname"),
                            rs.getString("firstname"), rs.getInt("age"));
                    entities.add(patient);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void add(Patient patient) throws RepoException {
        super.add(patient);

        try (var statement = conn.prepareStatement("INSERT INTO patients VALUES (?, ?, ?, ?)")) {
            statement.setInt(1, patient.getId());
            statement.setString(2, patient.getSurname());
            statement.setString(3, patient.getFirstname());
            statement.setInt(4, patient.getAge());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepoException("Eroare la salvarea pacientului: " + e.getMessage());
        }
    }

    public void remove(int id) throws RepoException {
        super.remove(id);

        try (var statement = conn.prepareStatement("DELETE FROM patients WHERE id = (?)")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepoException("Eroare la stergerea pacientului: " + e.getMessage());
        }
    }

    public Collection<Patient> getAll() {
        var patients = new ArrayList<Patient>();
        try(var statement = conn.prepareStatement("SELECT * FROM patients")){
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    patients.add(new Patient(rs.getInt("id"), rs.getString("surname"),
                            rs.getString("firstname"), rs.getInt("age")));
                }
            } catch (SQLException e) {
                throw new RepoException("Eroare la cautarea pacientilor: " + e.getMessage());
        }
        return patients;
    }

    public Patient get(int id) throws RepoException {
        try(var statement = conn.prepareStatement("SELECT * FROM patients WHERE id = (?)")){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                return new Patient(rs.getInt("id"), rs.getString("surname"),
                        rs.getString("firstname"), rs.getInt("age"));
            }
        } catch (SQLException e) {
            throw new RepoException("Eroare la cautarea pacientului: " + e.getMessage());
        }
        throw new RepoException("Element with this id not found=" + id);
    }

    public void update(Patient patient) throws RepoException {
        super.update(patient);

        try (var statement = conn.prepareStatement("UPDATE patients SET surname = (?), firstname = (?), age = (?) WHERE id = (?)")) {
            statement.setString(1, patient.getSurname());
            statement.setString(2, patient.getFirstname());
            statement.setInt(3, patient.getAge());
            statement.setInt(4, patient.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepoException("Eroare la actualizarea pacientului: " + e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
