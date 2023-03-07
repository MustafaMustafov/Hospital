package Project.Hospital.Controllers;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Entities.Doctor;
import Project.Hospital.Entities.Patient;
import Project.Hospital.Enums.AppointmentType;
import Project.Hospital.Repositories.AppointmentRepository;
import Project.Hospital.Repositories.DoctorRepository;
import Project.Hospital.Repositories.PatientRepository;
import Project.Hospital.Repositories.UserRepository;
import Project.Hospital.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PatientFunctionsController {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientService patientService;

    @GetMapping("/create-appointment")
    public String createAppointment(Model model) {
        Iterable <Doctor> doctors = doctorRepository.findAll();
        Appointment appointment = new Appointment();
        model.addAttribute("appointment",appointment);
        model.addAttribute("doctorList",doctors);
        model.addAttribute("appointmentType", AppointmentType.values());
        return "/patient/appointments/create";
    }

    @PostMapping("/submit-appointment")
    private ModelAndView saveAppointment(Appointment appointment) {
        patientService.setAppointmentPatient(appointment);
        appointmentRepository.save(appointment);
        return new ModelAndView("redirect:/");

    }

    @GetMapping("/index-appointment")
    public String showAppointment(Model model) {
        int id = patientService.loggedUserId();
        model.addAttribute("appointments",appointmentRepository.findByPatientId(id));
        return "/patient/appointments/index";
    }

    @GetMapping("/edit-appointment")
    public String editAppointment(Model model) {
        return "/patient/appointments/edit";
    }
}
