package Project.Hospital.Repositories;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Entities.Patient;
import Project.Hospital.Enums.AppointmentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    List<Appointment> findByPatientId(int id);
    List<Appointment> findByDoctorId(int id);
    List<Appointment> findBydoctorId(int id, Sort sort);
}
