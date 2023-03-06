package Project.Hospital.Repositories;

import Project.Hospital.Entities.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepositories extends CrudRepository<Appointment, Integer> {
}
