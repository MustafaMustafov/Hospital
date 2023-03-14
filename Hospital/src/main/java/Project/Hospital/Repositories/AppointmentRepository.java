package Project.Hospital.Repositories;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Entities.Doctor;
import Project.Hospital.Entities.Patient;
import Project.Hospital.Enums.AppointmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    List<Appointment> findByPatientId(int id);
    List<Appointment> findByDoctorId(int id);
    List<Appointment> findBydoctorId(int id, Sort sort);

    @Query("SELECT u FROM Appointment u\n" +
            "GROUP BY u.doctor.firstName \n" +
            " HAVING u.doctor.firstName = :doctorName")
    List<Appointment> groupByDoctorName(String doctorName);

    @Query("SELECT u FROM Appointment u\n" +
            "GROUP BY u.doctor.speciality \n" +
            " HAVING u.doctor.speciality = :specialty")
    List<Appointment> groupBySpecialty(String specialty);

    @Query("SELECT u FROM Appointment u\n" +
            "GROUP BY u.date \n" +
            " HAVING u.date = :date")
    List<Appointment> groupByDate(String date);
}
