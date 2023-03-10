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
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
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
        System.out.println(username);
        int id = 0;
        for (Patient pat : patients) {
            if (pat.getUser().getUsername().equals(username)){
                id=pat.getId();
            }
        }
        return id;
    }

    public String loggedUserName() {
        String patientName = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        Iterable<Patient> patients = patientRepository.findAll();
        System.out.println(username);
        int id = 0;
        for (Patient pat : patients) {
            if (pat.getUser().getUsername().equals(username)){
                patientName=pat.getFirstName();
            }
        }
        return patientName;
    }

    public Appointment setAppointmentPatient(Appointment appointment) {
        int id = loggedUserId();
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isPresent()){
            Patient patient1 = patient.get();
            appointment.setPatient(patient1);
            System.out.println(patient1);
        }
        return appointment;
    }

    public boolean checkDate(Appointment appointment) {
        boolean ifBefore = false;
        long millis=System.currentTimeMillis();
        Date date = new Date(millis);
        if (date.compareTo(appointment.getDate())>0) {
            ifBefore=true;
        }
        return ifBefore;
    }

    public boolean ifDateAndTimeEmpty(Appointment appointment) {
        boolean empty = true;
        String time = appointment.getTime();
        Date date = appointment.getDate();
        Iterable<Appointment> appointments = appointmentRepository.findByDoctorId(appointment.getDoctor().getId());
        for (Appointment app : appointments) {
            if (app.getDate().compareTo(date)==0)
                if(app.getTime().equals(time)) {
                    empty=false;
                }
        }
        return empty;
    }

}
