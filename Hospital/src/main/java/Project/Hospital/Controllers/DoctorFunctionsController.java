package Project.Hospital.Controllers;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Entities.Doctor;
import Project.Hospital.Enums.AppointmentType;
import Project.Hospital.Repositories.AppointmentRepository;
import Project.Hospital.Repositories.PatientRepository;
import Project.Hospital.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/doctor-controls")
public class DoctorFunctionsController {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorService doctorService;

    @GetMapping
    public String showDoctorHome(Model model) {
        Doctor loggedDoc = doctorService.loggedDoc();
        model.addAttribute("loggedDoc", loggedDoc);
        return "/doctor/index";
    }
    @GetMapping("/appointments")
    public String showAppointments(Model model) {
        int id = doctorService.loggedUserId();
        AppointmentType appType = AppointmentType.INITIAL;
        model.addAttribute("appointments",appointmentRepository.findByDoctorId(id));
        model.addAttribute("appointmentType", AppointmentType.values());
        model.addAttribute("appType",appType);
        return "/doctor/appointments/index";
    }

    @GetMapping("/all-appointments")
    public String showAllAppointments(Model model) {
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "/doctor/appointments/all";

    }

    @GetMapping("/group-appointments")
    public String groupBy(
            @RequestParam("radioGroup") String value,
            @RequestParam("doctorName") String doctorName,
            @RequestParam("specialty") String specialty,
            @RequestParam("date") String date,
            Model model
    ){
        List<Appointment> appointments = doctorService.groupAppointments(value, doctorName, specialty, date);
        model.addAttribute("appointments",appointments);
        return "doctor/appointments/all";
    }

    @PostMapping("/sort-appointments")
    private ModelAndView sortAppointments(@RequestParam(required = false,name="sort") String sort,
                                            @RequestParam(required = false,name="radioGroup") String order,
                                            Model model) {
        int id = doctorService.loggedUserId();
        List<Appointment> appointments = doctorService.appointmentOrder(order, sort,id);
        model.addAttribute("appointments",appointments);
        return new ModelAndView("/doctor/appointments/index");
    }
}
