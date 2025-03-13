package com.example.lab4;

import Domain.*;
import Repository.SQLPatientRepo;
import Repository.SQLAppointmentRepo;
import Service.AppointmentService;
import Service.PatientService;
import javafx.event.ActionEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import java.util.Map;

public class Controller {

    public TextField idPacientiTextField;
    public TextField numePacientTextField;
    public TextField prenumePacientTextField;
    public TextField varstaPacientTextField;
    public TextField idProgramareTextField;
    public TextField idPacientTextField;
    public TextField dataProgramareTextField;
    public TextField scopProgramareTextField;
    public ListView listView;
    public DialogPane dialogPane;
    public ListView listViewRapoarte;
    private AppointmentService appointmentService = new AppointmentService(new SQLAppointmentRepo());
    private PatientService patientService = new PatientService(new SQLPatientRepo());

    public void onAdaugaPacientButtonClick(ActionEvent actionEvent) {
        try{
            int id = Integer.parseInt(idPacientiTextField.getText());
            String surname = numePacientTextField.getText();
            String firstname = prenumePacientTextField.getText();
            int age = Integer.parseInt(varstaPacientTextField.getText());
            patientService.addPatient(id, surname, firstname, age);
            dialogPane.setContentText("Pacient adaugat cu succes!");
        } catch (Exception e) {
            dialogPane.setContentText(e.getMessage());
        }
        finally {
            idPacientiTextField.clear();
            numePacientTextField.clear();
            prenumePacientTextField.clear();
            varstaPacientTextField.clear();
        }
    }

    public void onModificaPacientButtonClick(ActionEvent actionEvent) {
        try{
            int id = Integer.parseInt(idPacientiTextField.getText());
            String surname = numePacientTextField.getText();
            String firstname = prenumePacientTextField.getText();
            int age = Integer.parseInt(varstaPacientTextField.getText());
            patientService.updatePatient(id, surname, firstname, age);
            dialogPane.setContentText("Pacient modificat cu succes!");
        } catch (Exception e) {
            dialogPane.setContentText(e.getMessage());
        }
        finally {
            idPacientiTextField.clear();
            numePacientTextField.clear();
            prenumePacientTextField.clear();
            varstaPacientTextField.clear();
        }
    }

    public void onStergePacientButtonClick(ActionEvent actionEvent) {
        try{
            int id = Integer.parseInt(idPacientiTextField.getText());
            patientService.deletePatient(id);
            dialogPane.setContentText("Pacient sters cu succes!");
        } catch (Exception e) {
            dialogPane.setContentText(e.getMessage());
        }
        finally {
            idPacientiTextField.clear();
            numePacientTextField.clear();
            prenumePacientTextField.clear();
            varstaPacientTextField.clear();
        }
    }

    public void onAfiseazaPacientiiButtonClick(ActionEvent actionEvent) {
        try{
            listView.getItems().clear();
            for (Patient patient : patientService.getAllPatients()) {
                listView.getItems().add(patient.toString());
            }
        } catch (Exception e) {
            dialogPane.setContentText(e.getMessage());
        }
    }

    public void onAdaugaProgramareButtonClick(ActionEvent actionEvent) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try{
            int id = Integer.parseInt(idProgramareTextField.getText());
            int idPatient = Integer.parseInt(idPacientTextField.getText());
            Date date = dateFormat.parse(dataProgramareTextField.getText());
            String purpose = scopProgramareTextField.getText();
            appointmentService.addAppointment(id, idPatient, date, purpose, patientService.getAllPatients());
            dialogPane.setContentText("Programare adaugata cu succes!");
        } catch (Exception e) {
            dialogPane.setContentText(e.getMessage());
        }
        finally {
            idProgramareTextField.clear();
            idPacientTextField.clear();
            dataProgramareTextField.clear();
            scopProgramareTextField.clear();
        }
    }

    public void onModificaProgramareButtonClick(ActionEvent actionEvent) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try{
            int id = Integer.parseInt(idProgramareTextField.getText());
            int idPatient = Integer.parseInt(idPacientTextField.getText());
            Date date = dateFormat.parse(dataProgramareTextField.getText());
            String purpose = scopProgramareTextField.getText();
            appointmentService.updateAppointment(id, idPatient, date, purpose, patientService.getAllPatients());
            dialogPane.setContentText("Programare modificata cu succes!");
        } catch (Exception e) {
            dialogPane.setContentText(e.getMessage());
        }
        finally {
            idProgramareTextField.clear();
            idPacientTextField.clear();
            dataProgramareTextField.clear();
            scopProgramareTextField.clear();
        }
    }

    public void onStergeProgramareButtonClick(ActionEvent actionEvent) {
        try{
            int id = Integer.parseInt(idProgramareTextField.getText());
            appointmentService.deleteAppointment(id);
            dialogPane.setContentText("Programare stearsa cu succes!");
        } catch (Exception e) {
            dialogPane.setContentText(e.getMessage());
        }
        finally {
            idProgramareTextField.clear();
            idPacientTextField.clear();
            dataProgramareTextField.clear();
            scopProgramareTextField.clear();
        }
    }

    public void onAfiseazaProgramarileButtonClick(ActionEvent actionEvent) {
        try{
            listView.getItems().clear();
            for (Appointment appointment : appointmentService.getAllAppointments()) {
                listView.getItems().add(appointment.toString());
            }
        } catch (Exception e) {
            dialogPane.setContentText(e.getMessage());
        }
    }

    public void onRaport1ButtonClick(ActionEvent actionEvent) {
        try {
            listViewRapoarte.getItems().clear();
            Map<Patient, Long> appointmentsCountByPatient = appointmentService.getAppointmentsCountByPatient();
            for (Map.Entry<Patient, Long> entry : appointmentsCountByPatient.entrySet()) {
                listViewRapoarte.getItems().add(entry.getKey().toString() + " : " + entry.getValue());
            }
        } catch (Exception e) {
            dialogPane.setContentText(e.getMessage());
        }
    }

    public void onRaport2ButtonClick(ActionEvent actionEvent) {
        try {
            listViewRapoarte.getItems().clear();
            Map<Integer, Long> appointmentsCountByMonth = appointmentService.getAppointmentsCountByMonth();
            for (Map.Entry<Integer, Long> entry : appointmentsCountByMonth.entrySet()) {
                listViewRapoarte.getItems().add("Luna " + entry.getKey() + " : " + entry.getValue());
            }
        } catch (Exception e) {
            dialogPane.setContentText(e.getMessage());
        }
    }

    public void onRaport3ButtonClick(ActionEvent actionEvent) {
        try {
            listViewRapoarte.getItems().clear();
            Map<Patient, Long> daysSinceLastAppointment = appointmentService.getDaysSinceLastAppointment();
            for (Map.Entry<Patient, Long> entry : daysSinceLastAppointment.entrySet()) {
                listViewRapoarte.getItems().add(entry.getKey().toString() + " : " + entry.getValue() + " zile");
            }
        } catch (Exception e) {
            dialogPane.setContentText(e.getMessage());
        }
    }
}