package Project.Hospital.Controllers;

import Project.Hospital.Entities.User;
import Project.Hospital.Enums.Roles;
import Project.Hospital.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        return "/auth/login";
    }

    @GetMapping()
    public String showMenu(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("DOCTOR"))) {
            return "/doctor/index";
        } else {
            return "/patient/index";
        }
    }

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "/auth/register";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Roles.PATIENT.toString());
        user.setEnabled(true);
        user.setUsername(user.getUsername());
        userRepository.save(user);
        return "register_success";
    }
}
