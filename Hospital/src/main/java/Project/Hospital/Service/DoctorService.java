package Project.Hospital.Service;

import Project.Hospital.Entities.Doctor;
import Project.Hospital.Entities.Patient;
import Project.Hospital.Repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;

    public int loggedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        Iterable<Doctor> doctors = doctorRepository.findAll();
        System.out.println(username);
        int id = 0;
        for (Doctor doc : doctors) {
            if (doc.getUser().getUsername().equals(username)){
                id=doc.getId();
            }
        }
        return id;
    }

    public String loggedUserName() {
        String doctorName = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        Iterable<Doctor> doctors = doctorRepository.findAll();
        System.out.println(username);
        int id = 0;
        for (Doctor doc : doctors) {
            if (doc.getUser().getUsername().equals(username)){
                doctorName=doc.getLastName();
            }
        }
        return doctorName;
    }
}
