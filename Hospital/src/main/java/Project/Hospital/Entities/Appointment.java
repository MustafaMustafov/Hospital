package Project.Hospital.Entities;

import Project.Hospital.Enums.AppointmentType;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
//    @JoinTable(name="doctors",
//            joinColumns =@JoinColumn(name="appointment_id"),
//            inverseJoinColumns = @JoinColumn(name="doctor_id"))
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = true, referencedColumnName = "id")
    private Doctor doctor;
    @OneToOne
//    @JoinTable(name="patients",
//                joinColumns =@JoinColumn(name="appointment_id"),
//                inverseJoinColumns = @JoinColumn(name="patient_id"))
    private Patient patient;
    private Date date;
    private String time;
    private AppointmentType appointmentType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }
}
