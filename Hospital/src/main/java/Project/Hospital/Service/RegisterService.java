package Project.Hospital.Service;

import Project.Hospital.Entities.User;
import Project.Hospital.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    UserRepository userRepository;

    public boolean existingUserCheck(User user){
    Iterable<User> users = userRepository.findAll();
    boolean existingUser = false;
        for (User usr: users) {
        if (usr.getUsername().equals(user.getUsername())) {
            existingUser=true;
            break;
            }
        }
        return existingUser;
    }


}
