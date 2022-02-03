package com.example.patientaccount.doctor.service;

import com.example.patientaccount.doctor.controller.DoctorController;
import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.repository.DoctorRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class DoctorService {
    private DoctorRepository doctorRepository;

    @Inject
    public DoctorService(DoctorRepository doctorRepository) {

        this.doctorRepository = doctorRepository;
    }

    public Optional<Doctor> find(String surname) {
        return doctorRepository.find(surname);
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Transactional
    public void create(Doctor doctor) {
        doctorRepository.create(doctor);
    }
    @Transactional
    public void update(Doctor doctor) {
        doctorRepository.update(doctor);
    }

    @Transactional
    public void delete(String doctor) {
        doctorRepository.delete(doctorRepository.find(doctor).orElseThrow());
    }
}
