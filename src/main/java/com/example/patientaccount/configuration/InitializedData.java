package com.example.patientaccount.configuration;

import com.example.patientaccount.Role;
import com.example.patientaccount.Specialisation;
import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.entity.Visit;
import com.example.patientaccount.doctor.service.DoctorService;
import com.example.patientaccount.doctor.service.VisitService;
import lombok.SneakyThrows;

import com.example.patientaccount.user.entity.User;
import com.example.patientaccount.user.service.UserService;
import com.example.patientaccount.digest.Sha256Utility;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.control.RequestContextController;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Listener started automatically on servlet context initialized. Fetches instance of the datasource from the servlet
 * context and fills it with default content. Normally this class would fetch database datasource and init data only
 * in cases of empty database. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@ApplicationScoped
public class InitializedData {
    private final UserService userService;
    private final DoctorService doctorService;
    private final VisitService visitService;
    private RequestContextController requestContextController;

    @Inject
    public InitializedData(UserService userService, DoctorService doctorService, VisitService visitService, RequestContextController requestContextController) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.visitService = visitService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should
     * be created only once.
     */
    private synchronized void init() {
        requestContextController.activate();// start request scope in order to inject request scoped repositories

        User admin = User.builder()
                .login("admin")
                .name("Adam")
                .surname("Cormel")
                .birthDate(LocalDate.of(1990, 10, 21))
                .personalIdNumber(90102109233L)
                .phoneNumber("809789660")
                .role(Role.ADMIN)
                .email("admin@simplerpg.example.com")
                .password(Sha256Utility.hash("adminadmin"))
                .portraitPath("")
                .build();

        User kevin = User.builder()
                .login("kevin")
                .name("Kevin")
                .surname("Pear")
                .birthDate(LocalDate.of(2011, 1, 16))
                .personalIdNumber(11011608234L)
                .phoneNumber("354676888")
                .role(Role.USER)
                .email("kevin@example.com")
                .password(Sha256Utility.hash("useruser"))
                //.portraitPath("/home/student/Dokumenty/avatars/4.png")
                .portraitPath("D:/Rozalia/semestr_7/JEE/avatars/4.png")
                .build();

        User alice = User.builder()
                .login("alice")
                .name("Alice")
                .surname("Grape")
                .birthDate(LocalDate.of(2012, 3, 19))
                .personalIdNumber(12031902233L)
                .phoneNumber("123456789")
                .role(Role.USER)
                .email("kevin@example.com")
                .password(Sha256Utility.hash("useruser"))
                .portraitPath("")
                .build();

        User ben = User.builder()
                .login("ben")
                .name("Ben")
                .surname("Potter")
                .birthDate(LocalDate.of(1997, 8, 20))
                .personalIdNumber(97082022345L)
                .phoneNumber("669701234")
                .role(Role.USER)
                .email("ben@example.com")
                .password(Sha256Utility.hash("useruser"))
                .portraitPath("")
                .build();

        userService.create(admin);
        userService.create(kevin);
        userService.create(alice);
        userService.create(ben);

        Doctor anna = Doctor.builder()
                .name("Anna")
                .surname("Adoctor")
                .specialisation(Specialisation.ALLERGIST)
                .stars(3.0)
                .registrar(true)
                .visits(new ArrayList<Visit>())
                .build();
        Doctor rose = Doctor.builder()
                .name("Rose")
                .surname("Rdoc")
                .specialisation(Specialisation.INTERNIST)
                .stars(4.0)
                .registrar(true)
                .visits(new ArrayList<Visit>())
                .build();
        Doctor penny = Doctor.builder()
                .name("Penny")
                .surname("Pdoct")
                .specialisation(Specialisation.PAEDIATRICIAN)
                .stars(3.5)
                .registrar(false)
                .visits(new ArrayList<Visit>())
                .build();

        doctorService.create(anna);
        doctorService.create(rose);
        doctorService.create(penny);

        String date = "10-11-2020";
        String time = "10:00";

        Visit visit_1 = Visit.builder()
                .date(date)
                .hour(time)
                .time(1)
                .consultation(true)
                .treatment(false)
                .doctor(penny).build();
        Visit visit_2 = Visit.builder()
                .date(date)
                .hour(time)
                .time(4)
                .consultation(true)
                .treatment(false)
                .doctor(penny).build();
        Visit visit_3 = Visit.builder()
                .date(date)
                .hour(time)
                .time(3)
                .consultation(true)
                .treatment(false)
                .doctor(anna).build();
        Visit visit_4 = Visit.builder()
                .date(date)
                .hour(time)
                .time(2)
                .consultation(true)
                .treatment(false)
                .doctor(rose).build();

        visitService.create(visit_1);
        visitService.create(visit_2);
        visitService.create(visit_3);
        visitService.create(visit_4);

        requestContextController.deactivate();
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }
}