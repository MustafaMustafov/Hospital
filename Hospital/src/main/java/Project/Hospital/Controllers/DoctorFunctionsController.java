package Project.Hospital.Controllers;

import Project.Hospital.Repositories.AppointmentRepository;
import Project.Hospital.Repositories.DoctorRepository;
import Project.Hospital.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DoctorFunctionsController {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    DoctorService doctorService;

    @GetMapping("/doctor-home")
    public String showHome() {
        return "/doctor/index";
    }
    @GetMapping("/doctor-appointments")
    public String showAppointments(Model model) {
        int id = doctorService.loggedUserId();
        model.addAttribute("appointments",appointmentRepository.findByDoctorId(id));
        return "/doctor/appointments/index";
    }

    @GetMapping("/doctor-appointments/group-appointments")
    public String groupAppointments(Model model) {
        int id = doctorService.loggedUserId();
        model.addAttribute("appointments",appointmentRepository.findByDoctorId(id));
        return "/doctor/appointments/group-appointments";
    }

    @GetMapping("/doctor-appointments/sort-appointments")
    public String sortAppointments(Model model) {
        int id = doctorService.loggedUserId();
        model.addAttribute("appointments",appointmentRepository.findByDoctorId(id));
        return "/doctor/appointments/sort-appointments";
    }

}
