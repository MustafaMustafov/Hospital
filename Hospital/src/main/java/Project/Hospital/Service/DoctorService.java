package Project.Hospital.Service;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Entities.Doctor;
import Project.Hospital.Repositories.AppointmentRepository;
import Project.Hospital.Repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    AppointmentRepository appointmentRepository;

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

    public Doctor loggedDoc() {
        Doctor doctor = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        Iterable<Doctor> doctors = doctorRepository.findAll();
        int id = 0;
        for (Doctor doc : doctors) {
            if (doc.getUser().getUsername().equals(username)){
                doctor = doc;
            }
        }
        return doctor;
    }

    public String sortBy(String sort) {
        String sortBy = "";
        if (sort.equals("Patient ID")) {
            sortBy="patient.id";
        } else if (sort.equals("Patient name")) {
            sortBy="patient.firstName";
        } else {
            sortBy="time";
        }
        return sortBy;
    }

    public List<Appointment> appointmentOrder(String order, String sort) {
        int doctorId = loggedUserId();
        List<Appointment> appointments;
        if (order.equals("desc")){
            appointments = appointmentRepository.findBydoctorId(doctorId, Sort.by(sortBy(sort)).descending());
        } else {
            appointments = appointmentRepository.findBydoctorId(doctorId,Sort.by(sortBy(sort)).ascending());
        }
        return appointments;
    }

    public void groupAppointments(String value, String doctorName, Model model, String specialty, String date) {
        List<Appointment> appointments = null;
        if (!value.equalsIgnoreCase("docName")) {
            if (value.equalsIgnoreCase("specialty")) {
                appointments = appointmentRepository.groupBySpecialty(specialty);
            } else if (value.equalsIgnoreCase("date")) {
                appointments = appointmentRepository.groupByDate(date);
            }
        } else {
            appointments = appointmentRepository.groupByDoctorName(doctorName);
        }

        model.addAttribute("appointments", appointments);
    }
}
