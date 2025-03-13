package com.example.lab4;

import Domain.Appointment;
import Domain.AppointmentConverter;
import Domain.Patient;
import Domain.PatientConverter;
import Repository.*;
import Service.AppointmentService;
import Service.PatientService;
import Ui.Console;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = MyApplication.class.getResource("hello-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
        stage.setTitle("MyApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        SimpleDateFormat dateFormat;
        Properties properties = new Properties();
        try {
            FileInputStream input = new FileInputStream("srcs/main/src/config.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (properties.getProperty("interface").equals("True")) {
            launch();
        }
        else if(properties.getProperty("interface").equals("False")) {
        PatientService patient_service = null;
        AppointmentService appointment_service = null;

        if(properties.getProperty("repository.type").equals("memory")) {
            MemoryRepo<Patient> patient_repo = new MemoryRepo<>();
            patient_repo.add(new Patient(1, "Popescu", "Ion", 23));
            patient_repo.add(new Patient(2, "Ionescu", "Maria", 45));
            patient_repo.add(new Patient(3, "Georgescu", "Vasile", 34));
            patient_repo.add(new Patient(4, "Popa", "Mihai", 56));
            patient_repo.add(new Patient(5, "Ionescu", "Ana", 67));
            MemoryRepo<Appointment> appointment_repo = new MemoryRepo<>();
            dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                appointment_repo.add(new Appointment(1, patient_repo.get(5), dateFormat.parse("12/11/2023 14:00"), "Control"));
                appointment_repo.add(new Appointment(2, patient_repo.get(2), dateFormat.parse("03/07/2023 15:30"), "Operatie"));
                appointment_repo.add(new Appointment(3, patient_repo.get(4), dateFormat.parse("30/02/2023 11:00"), "Control"));
                appointment_repo.add(new Appointment(4, patient_repo.get(3), dateFormat.parse("22/05/2023 10:30"), "Plomba"));
                appointment_repo.add(new Appointment(5, patient_repo.get(1), dateFormat.parse("17/10/2023 18:00"), "Plomba"));
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
            patient_service = new PatientService(patient_repo);
            appointment_service = new AppointmentService(appointment_repo);
            Console console = new Console(patient_service, appointment_service);
            console.run();
        } else if(properties.getProperty("repository.type").equals("text")) {
            TextFileRepo<Patient> patient_repo = new TextFileRepo<>("srcs/main/src/patients.txt", new PatientConverter());
            TextFileRepo<Appointment> appointment_repo = new TextFileRepo<>("srcs/main/src/appointments.txt", new AppointmentConverter());
            patient_service = new PatientService(patient_repo);
            appointment_service = new AppointmentService(appointment_repo);
        } else if(properties.getProperty("repository.type").equals("binary")) {
            BinaryFileRepo<Patient> patient_repo = new BinaryFileRepo<>("patients.bin");
            BinaryFileRepo<Appointment> appointment_repo = new BinaryFileRepo<>("appointments.bin");
            patient_service = new PatientService(patient_repo);
            appointment_service = new AppointmentService(appointment_repo);
        }
        else if(properties.getProperty("repository.type").equals("sql")) {
            SQLPatientRepo patient_repo = new SQLPatientRepo();
            SQLAppointmentRepo appointment_repo = new SQLAppointmentRepo();
            patient_service = new PatientService(patient_repo);
            appointment_service = new AppointmentService(appointment_repo);
        }
        else {
            System.out.println("Invalid repository type");
            return;
        }
        Console console = new Console(patient_service, appointment_service);
        console.run();
    }
        else {
            System.out.println("Option for interface is invalid");
        }
    }
}