package Project.Hospital.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoctorFunctionsController {
    @GetMapping("/doctor-appointments")
    public String showAppointments() {
        return "/doctor/appointments/index";
    }


}
