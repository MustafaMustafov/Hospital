package Project.Hospital;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Entities.Doctor;
import Project.Hospital.Entities.Patient;
import Project.Hospital.Entities.User;
import Project.Hospital.Repositories.AppointmentRepository;
import Project.Hospital.Repositories.DoctorRepository;
import Project.Hospital.Repositories.PatientRepository;
import Project.Hospital.Service.DoctorService;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DoctorServiceTest {
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    DoctorService doctorService;

    @Mock
    AppointmentRepository appointmentRepository;

    @Test
    public void loggedUserIdTest() {
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        UserDetails userDetails = mock(UserDetails.class);
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        Doctor doctor = mock(Doctor.class);
        User user = mock(User.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn(userDetails);
        Mockito.when(userDetails.getUsername()).thenReturn("john");
        Mockito.when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor));
        Mockito.when(doctor.getUser()).thenReturn(user);
        Mockito.when(user.getUsername()).thenReturn("john");
        Mockito.when(doctor.getId()).thenReturn(1);

        int userId = doctorService.loggedUserId();
        assertEquals(0, userId);
    }

    @Test
    public void sortByTest() {
        String sort = "Patient ID";
        String sortBy = doctorService.sortBy(sort);
        assertEquals("patient.id",sortBy);
    }

    @Test
    public void appointmentOrderTest() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("Ivan");
        doctor.setId(1);

        List<Appointment> appointments = new ArrayList<>();

        Appointment appointment1 = new Appointment();
        appointment1.setDate("2023-03-15");
        appointment1.setTime("13:10");
        appointment1.setDoctor(doctor);

        Appointment appointment2 = new Appointment();
        appointment2.setDate("2023-03-15");
        appointment2.setTime("13:50");
        appointment2.setDoctor(doctor);

        Appointment appointment3 = new Appointment();
        appointment3.setDate("2023-03-15");
        appointment3.setTime("14:40");
        appointment3.setDoctor(doctor);

        appointments.add(appointment1);
        appointments.add(appointment2);
        appointments.add(appointment3);

        String sort = "time";
        String order = "asc";
        Mockito.when(appointmentRepository.findBydoctorId(1, Sort.by(sort).ascending())).thenReturn(appointments);

        List<Appointment> appointmentList = doctorService.appointmentOrder(order,sort,1);
        assertEquals(appointments.get(0),appointmentList.get(0));
        assertEquals(appointments.get(1),appointmentList.get(1));
        assertEquals(appointments.get(2),appointmentList.get(2));
    }

    @Test
    public void groupAppointmentsTest() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("Ivan");
        doctor.setId(1);

        List<Appointment> appointmentListDoctorIvan = new ArrayList<>();

        Appointment appointment2 = new Appointment();
        appointment2.setDate("2023-03-15");
        appointment2.setTime("13:50");
        appointment2.setDoctor(doctor);

        Appointment appointment3 = new Appointment();
        appointment3.setDate("2023-03-15");
        appointment3.setTime("14:40");
        appointment3.setDoctor(doctor);

        appointmentListDoctorIvan.add(appointment2);
        appointmentListDoctorIvan.add(appointment3);

        Mockito.when(appointmentRepository.groupByDoctorName("Ivan")).thenReturn(appointmentListDoctorIvan);
        List<Appointment> appointmentList = doctorService.groupAppointments("docName","Ivan","","");

        verify(appointmentRepository).groupByDoctorName("Ivan");

        assertEquals(appointmentListDoctorIvan,appointmentList);

    }
}
