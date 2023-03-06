package Project.Hospital.Entities;

import Project.Hospital.Enums.AppointmentTypes;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @OneToOne
    private Doctor doctor;
    @OneToOne
    private Patient patient;
    private Date date;
    private AppointmentTypes appointmentTypes;

}
