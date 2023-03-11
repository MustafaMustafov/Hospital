package Project.Hospital.Controllers;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Enums.AppointmentType;
import Project.Hospital.Repositories.AppointmentRepository;
import Project.Hospital.Repositories.DoctorRepository;
import Project.Hospital.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;
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
        int id = doctorService.loggedUserId();
        Iterable<Appointment> appointments = appointmentRepository.findByDoctorId(id);
        for (Appointment app : appointments) {


        }
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/filter-appointments")
    private ModelAndView filterAppointments(@RequestParam(name="patientName") String patientName,
                                            @RequestParam(name="appType") String appType,
                                            @RequestParam(name="patId") String patId,
                                            Model model) {
        int id = doctorService.loggedUserId();
        Iterable<Appointment> appointments = appointmentRepository.findByDoctorId(id);
        List<Appointment> appointments1 = new ArrayList<>();
        for (Appointment app: appointments) {
            if (!patientName.equals("")) {
                if (patientName.equals(app.getPatient().getFirstName())) {
                    if (!appType.equals("")) {
                        if (appType.equals(app.getAppointmentType())) {
                            if (!patId.equals("")) {
                                if (patId.equals(app.getPatient().getId())) {
                                    appointments1.add(app);
                                }
                            }
                        }
                    }
                }
            } else if (!appType.equals("")) {
                if (appType.equals(app.getAppointmentType())) {
                    if (!patId.equals("")) {
                        if (patId.equals(app.getPatient().getId())) {
                            appointments1.add(app);
                        }
                    }
                }
            } else if (!patId.equals("")) {
                if (!patId.equals("")) {
                    if (patId.equals(app.getPatient().getId())) {
                        appointments1.add(app);
                    }
                }
            } else {
                appointments1.add(app);
            }
        }
        model.addAttribute("appointments",appointments1);
        return new ModelAndView("/doctor/appointments/index");
    }


}
