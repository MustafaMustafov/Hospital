package Project.Hospital.Repositories;

import Project.Hospital.Entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public User getUserByUsername(String username);
}
