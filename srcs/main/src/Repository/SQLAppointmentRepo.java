package Repository;

import Domain.Appointment;
import Domain.PatientConverter;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class SQLAppointmentRepo extends MemoryRepo<Appointment> implements AutoCloseable {
    private static final String JDBC_URL =
            "jdbc:sqlite:srcs/main/src/appointments.db";

    private Connection conn = null;

    public SQLAppointmentRepo() {
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
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS appointments(id int primary key, patient varchar(200), dates date, purpose varchar(100));");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from appointments");
                 ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    PatientConverter patientConverter = new PatientConverter();
                    Appointment appointment = new Appointment(rs.getInt("id"), patientConverter.fromString(rs.getString("patient")),
                            rs.getDate("dates"), rs.getString("purpose"));
                    entities.add(appointment);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//    todo repara aici
    public void add(Appointment appointment) throws RepoException {
        super.add(appointment);

        try (var statement = conn.prepareStatement("INSERT INTO appointments VALUES (?, ?, ?, ?)")) {
            statement.setInt(1, appointment.getId());
            statement.setString(2, appointment.getPatient().toCSVString());
            statement.setDate(3, appointment.getSQLDate());
            statement.setString(4, appointment.getPurpose());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepoException("Eroare la salvarea programarii: " + e.getMessage());
        }
    }

    public void remove(int id) throws RepoException {
        super.remove(id);

        try (var statement = conn.prepareStatement("DELETE FROM appointments WHERE id = (?)")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepoException("Eroare la stergerea programarii: " + e.getMessage());
        }
    }

    public Collection<Appointment> getAll() {
        var appointments = new ArrayList<Appointment>();
        try(var statement = conn.prepareStatement("SELECT * FROM appointments")){
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    PatientConverter patientConverter = new PatientConverter();
                    Appointment appointment = new Appointment(rs.getInt("id"), patientConverter.fromString(rs.getString("patient")),
                            rs.getDate("dates"), rs.getString("purpose"));
                    appointments.add(appointment);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public Appointment get(int id) throws RepoException {
        try(var statement = conn.prepareStatement("SELECT * FROM appointments WHERE id = (?)")){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                PatientConverter patientConverter = new PatientConverter();
                return new Appointment(rs.getInt("id"), patientConverter.fromString(rs.getString("patient")),
                        rs.getDate("dates"), rs.getString("purpose"));
            }
        } catch (SQLException e) {
            throw new RepoException("Eroare la cautarea programarii: " + e.getMessage());
        }
        throw new RepoException("Element with id not found=" + id);
    }

    public void update(Appointment appointment) throws RepoException {
        if (entities.isEmpty()) {
            throw new RepoException("Nicio entitate in repository");
        }
        for (Appointment entity : entities) {
            if (entity.getId() == appointment.getId()) {
                entities.remove(entity);
                entities.add(appointment);
                try (var statement = conn.prepareStatement("UPDATE appointments SET patient = ?, dates = ?, purpose = ? WHERE id = ?")) {
                    statement.setString(1, appointment.getPatient().toCSVString());
                    statement.setDate(2, appointment.getSQLDate());
                    statement.setString(3, appointment.getPurpose());
                    statement.setInt(4, appointment.getId());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new RepoException("Eroare la actualizarea programarii: " + e.getMessage());
                }
                return;
            }
        }
        throw new RepoException("Entitatea cu acest id nu exista");
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
