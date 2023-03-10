package Project.Hospital.Controllers;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Entities.Doctor;
import Project.Hospital.Enums.AppointmentType;
import Project.Hospital.Repositories.AppointmentRepository;
import Project.Hospital.Repositories.DoctorRepository;
import Project.Hospital.Repositories.UserRepository;
import Project.Hospital.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.text.DateFormat;

@Controller
public class PatientFunctionsController {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientService patientService;

    @GetMapping("/patient-home")
    public String showPatientHome(Model model) {
        String patientName = patientService.loggedUserName();
        model.addAttribute("patientName", patientName);
        return "/patient/index";
    }
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
        if (patientService.checkDate(appointment)) {
            return new ModelAndView("redirect:/create-appointment");
        } else if (!patientService.ifDateAndTimeEmpty(appointment)) {
            return new ModelAndView("redirect:/create-appointment");
        }
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
        int id = patientService.loggedUserId();
        model.addAttribute("appointments",appointmentRepository.findByPatientId(id));
        return "/patient/appointments/edit";
    }

    @GetMapping("/cancel-appointment")
    public String cancelAppointment(Model model) {
        int id = patientService.loggedUserId();
        model.addAttribute("appointments",appointmentRepository.findByPatientId(id));
        return "/patient/appointments/cancel";
    }

    @PostMapping("/cancel-appointment/{appId}")
    private ModelAndView deleteAppointment(@PathVariable(name="appId")Integer appId) {
        System.out.println(appId);
        appointmentRepository.deleteById(appId);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/edit-appointment/{appId}")
    public String editAppointment(@PathVariable(name="appId") Integer appId, Model model) {
        model.addAttribute("doctorList",doctorRepository.findAll());
        model.addAttribute("appointmentType",AppointmentType.values());
        model.addAttribute("appointment",appointmentRepository.findById(appId));
        return "/patient/appointments/edit-appointment";
    }

    @PostMapping("/update-appointment")
    private ModelAndView updateAppointment(Appointment appointment) {
        if (patientService.checkDate(appointment)) {
            return new ModelAndView("redirect:/edit-appointment");
        } else if (!patientService.ifDateAndTimeEmpty(appointment)) {
            return new ModelAndView("redirect:/edit-appointment");
        }
        patientService.setAppointmentPatient(appointment);
        appointmentRepository.save(appointment);
        return new ModelAndView("redirect:/");
    }


}
