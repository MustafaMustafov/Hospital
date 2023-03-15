package Project.Hospital.Repositories;

import Project.Hospital.Entities.Appointment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    List<Appointment> findByPatientId(int id);
    List<Appointment> findByDoctorId(int id);
    List<Appointment> findBydoctorId(int id, Sort sort);

    @Query("SELECT u FROM Appointment u\n" +
            "WHERE u.doctor.firstName = :doctorName")
    List<Appointment> groupByDoctorName(String doctorName);

    @Query("SELECT u FROM Appointment u\n" +
            "WHERE u.doctor.speciality = :specialty")
    List<Appointment> groupBySpecialty(String specialty);

    @Query("SELECT u FROM Appointment u\n" +
            "WHERE u.date = :date ")
    List<Appointment> groupByDate(String date);
}
