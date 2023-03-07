package Project.Hospital.Service;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Entities.Patient;
import Project.Hospital.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

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
}
