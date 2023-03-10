package Project.Hospital.Repositories;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Entities.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    List<Appointment> findByPatientId(int id);
    List<Appointment> findByDoctorId(int id);
}
