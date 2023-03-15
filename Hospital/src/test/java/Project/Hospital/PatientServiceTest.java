package Project.Hospital;

import Project.Hospital.Entities.Appointment;
import Project.Hospital.Entities.Doctor;
import Project.Hospital.Entities.Patient;
import Project.Hospital.Entities.User;
import Project.Hospital.Repositories.AppointmentRepository;
import Project.Hospital.Repositories.PatientRepository;
import Project.Hospital.Service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientServiceTest {
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @InjectMocks
    private PatientService patientService;

    @Test
    public void setAppointmentPatientTest() {
        User user = new User();
        user.setId(1);

        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("ivan");
        patient.setUser(user);

        Appointment appointment = new Appointment();

        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
        appointment = patientService.setAppointmentPatient(appointment,user.getId());
        assertEquals(appointment.getPatient(), patient);
    }

    @Test
    public void checkDateTest() {
        Doctor doctor = new Doctor();
        doctor.setId(1);

        Patient patient = new Patient();
        patient.setId(1);

        Appointment appointment = new Appointment();
        appointment.setDate("2023-03-15");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        boolean result = patientService.checkDate(appointment);
        assertEquals(true,result);
    }

    @Test
    public void isDateAndTimeEmptyTest() {
        Doctor doctor = new Doctor();
        doctor.setId(1);

        Patient patient = new Patient();
        patient.setId(1);

        Appointment appointment1 = new Appointment();
        appointment1.setDate("2023-03-15");
        appointment1.setTime("13:50");
        appointment1.setPatient(patient);
        appointment1.setDoctor(doctor);

        List<Appointment> appointments = new ArrayList<>();

        Appointment appointment2 = new Appointment();
        appointment2.setDate("2023-03-15");
        appointment2.setTime("13:10");
        appointment2.setPatient(patient);
        appointment2.setDoctor(doctor);

        Appointment appointment3 = new Appointment();
        appointment3.setDate("2023-03-15");
        appointment3.setTime("13:50");
        appointment3.setPatient(patient);
        appointment3.setDoctor(doctor);

        appointments.add(appointment1);
        appointments.add(appointment2);
        appointments.add(appointment3);

        Mockito.when(appointmentRepository.findByDoctorId(1)).thenReturn(appointments);
        boolean result = patientService.isDateAndTimeEmpty(appointment1);
        assertEquals(false,result);
    }

    @Test
    public void isEnoughTimeTest() {
        Doctor doctor = new Doctor();
        doctor.setId(1);

        Appointment appointment = new Appointment();
        appointment.setDate("2023-03-15");
        appointment.setTime("14:20");
        appointment.setDoctor(doctor);

        List<Appointment> appointments = new ArrayList<>();

        Appointment appointment1 = new Appointment();
        appointment1.setDate("2023-03-15");
        appointment1.setTime("13:50");
        appointment1.setDoctor(doctor);

        Appointment appointment2 = new Appointment();
        appointment2.setDate("2023-03-15");
        appointment2.setTime("13:10");
        appointment2.setDoctor(doctor);

        Appointment appointment3 = new Appointment();
        appointment3.setDate("2023-03-15");
        appointment3.setTime("14:40");
        appointment3.setDoctor(doctor);

        appointments.add(appointment1);
        appointments.add(appointment2);
        appointments.add(appointment3);

        Mockito.when(appointmentRepository.findByDoctorId(1)).thenReturn(appointments);
        boolean result = patientService.isEnoughTime(appointment);
        assertEquals(false,result);
    }

    @Test
    public void loggedUserIdTest() {
        Authentication auth = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        PatientRepository patientRepository = Mockito.mock(PatientRepository.class);
        Patient patient = Mockito.mock(Patient.class);
        User user = Mockito.mock(User.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn(userDetails);
        Mockito.when(userDetails.getUsername()).thenReturn("john");
        Mockito.when(patientRepository.findAll()).thenReturn(Arrays.asList(patient));
        Mockito.when(patient.getUser()).thenReturn(user);
        Mockito.when(user.getUsername()).thenReturn("john");
        Mockito.when(patient.getId()).thenReturn(1);

        int userId = patientService.loggedUserId();
        assertEquals(0, userId);
    }
}




