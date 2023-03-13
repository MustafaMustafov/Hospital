package Project.Hospital.Controllers;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Enums.AppointmentType;
import Project.Hospital.Repositories.AppointmentRepository;
import Project.Hospital.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DoctorFunctionsController {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    DoctorService doctorService;

    @GetMapping("/doctor-home")
    public String showDoctorHome(Model model) {
        String doctorName = doctorService.loggedUserName();
        model.addAttribute("doctorName", doctorName);
        return "/doctor/index";
    }
    @GetMapping("/doctor-appointments")
    public String showAppointments(Model model) {
        int id = doctorService.loggedUserId();
        AppointmentType appType = AppointmentType.INITIAL;
        model.addAttribute("appointments",appointmentRepository.findByDoctorId(id));
        model.addAttribute("appointmentType", AppointmentType.values());
        model.addAttribute("appType",appType);
        return "/doctor/appointments/index";
    }

//    @GetMapping("filter-appointments")
//    public String groupAppointments(Model model) {
//        int id = doctorService.loggedUserId();
//        model.addAttribute("appointments",appointmentRepository.findByDoctorId(id));
//        return "/doctor/appointments/group-appointments";
//    }
//
//    @GetMapping("group-appointments")
//    public String sortAppointments(Model model) {
//        int id = doctorService.loggedUserId();
//        model.addAttribute("appointments",appointmentRepository.findByDoctorId(id));
//        return "/doctor/appointments/sort-appointments";
//    }

    @PostMapping("/group-appointments")
    public ModelAndView groupAppointments() {
        // last functionality to do
        int id = doctorService.loggedUserId();
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/sort-appointments")
    private ModelAndView sortAppointments(@RequestParam(required = false,name="sort") String sort,
                                            @RequestParam(required = false,name="radioGroup") String order,
                                            Model model) {
        List<Appointment> appointments = doctorService.appointmentOrder(order, sort);
        model.addAttribute("appointments",appointments);
        return new ModelAndView("/doctor/appointments/index");
    }
}
