package Project.Hospital.Service;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Entities.Patient;
import Project.Hospital.Repositories.AppointmentRepository;
import Project.Hospital.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    AppointmentRepository appointmentRepository;

    public int loggedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        Iterable<Patient> patients = patientRepository.findAll();
        int id = 0;
        for (Patient pat : patients) {
            if (pat.getUser().getUsername().equals(username)){
                id=pat.getId();
            }
        }
        return id;
    }

//    public String loggedUserName() {
//        String patientName = "";
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) auth.getPrincipal();
//        String username = userDetails.getUsername();
//        Iterable<Patient> patients = patientRepository.findAll();
//        System.out.println(username);
//        int id = 0;
//        for (Patient pat : patients) {
//            if (pat.getUser().getUsername().equals(username)){
//                patientName=pat.getFirstName();
//            }
//        }
//        return patientName;
//    }

    public Appointment setAppointmentPatient(Appointment appointment,int id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isPresent()){
            Patient patient1 = patient.get();
            appointment.setPatient(patient1);
            System.out.println(patient1);
        }
        return appointment;
    }

    public boolean checkDate(Appointment appointment) {
        boolean isBefore = false;
        long millis=System.currentTimeMillis();
        Date date = new Date(millis);
        if (date.compareTo(appointment.getSQLDate())>0) {
            isBefore=true;
        }
        return isBefore;
    }

    public boolean isDateAndTimeEmpty(Appointment appointment) {
        boolean empty = true;
        String time = appointment.getTime();
        Date date = appointment.getSQLDate();
        Iterable<Appointment> appointments = appointmentRepository.findByDoctorId(appointment.getDoctor().getId());
        for (Appointment app : appointments) {
            if (app.getSQLDate().compareTo(date)==0)
                if(app.getTime().equals(time)) {
                    empty=false;
                }
        }

        return empty;
    }

    public boolean isEnoughTime(Appointment appointment) {
        String time = appointment.getTime();
        boolean enoughTime = true;
        Iterable<Appointment> appointments = appointmentRepository.findByDoctorId(appointment.getDoctor().getId());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            java.util.Date d1 = sdf.parse(time);
            for (Appointment app: appointments) {
                java.util.Date d2 = sdf.parse(app.getTime());
                if (d1.getTime() > d2.getTime()) {
                    long elapsed = d1.getTime() - d2.getTime();
                    if (elapsed<1800000){
                        enoughTime = false;
                    } else {
                        enoughTime = true;
                    }
                } else {
                    long elapsed = d2.getTime()-d1.getTime();
                    if(elapsed<1800000) {
                        enoughTime = false;
                    } else {
                        enoughTime = true;
                    }
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    return enoughTime;
    }

    public Patient loggedPat() {
        Patient patient = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        Iterable<Patient> patients = patientRepository.findAll();
        int id = 0;
        for (Patient pat : patients) {
            if (pat.getUser().getUsername().equals(username)){
                patient = pat;
            }
        }
        return patient;
    }

}
