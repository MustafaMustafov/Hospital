package Project.Hospital.Entities;

import Project.Hospital.Enums.AppointmentType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    private Patient patient;
    private String date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public Date getSQLDate() {
        java.sql.Date sqlStartDate = null;

        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(this.date);
            sqlStartDate = new java.sql.Date(date.getTime());
        } catch (Exception e) {
            System.out.println(e);
        }

        return sqlStartDate;
    }
}
