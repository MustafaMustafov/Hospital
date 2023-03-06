package Project.Hospital.Controllers;

import Project.Hospital.Entities.User;
import Project.Hospital.Enums.Roles;
import Project.Hospital.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.management.relation.Role;

@Controller
public class LoginSignUpController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String viewHomePage() {
        return "login";
    }

    @GetMapping("/index")
    public String showMenu(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().equals("DOCTOR")) {
            return "/doctor/doctor-index";
        } else {
            return "/patient/patient-index";
        }
    }

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles",Roles.values());
        return "signup";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(user.getRole());
        user.setEnabled(true);
        user.setUsername(user.getUsername());
        userRepository.save(user);
        return "register_success";
    }
}
