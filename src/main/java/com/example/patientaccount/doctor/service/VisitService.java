package com.example.patientaccount.doctor.service;

import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.entity.Visit;
import com.example.patientaccount.doctor.repository.DoctorRepository;
import com.example.patientaccount.doctor.repository.VisitRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.print.Doc;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class VisitService {
    private VisitRepository visitRepository;
    private DoctorRepository doctorRepository;

    @Inject
    public VisitService(VisitRepository repository, DoctorRepository doctorRepository) {
        this.visitRepository = repository;
        this.doctorRepository = doctorRepository;
    }

    public Optional<Visit> find(Long id) {
        return visitRepository.find(id);
    }

    @Transactional
    public void create(Visit visit) {
        visitRepository.create(visit);
        doctorRepository.find(visit.getDoctor().getSurname()).ifPresent(doctor -> doctor.getVisits().add(visit));
    }

    public List<Visit> findAll() {
        return visitRepository.findAll();
    }

    public List<Visit> findAllForDoctor(String surname) {
        Optional<Doctor> doctor = doctorRepository.find(surname);
        return visitRepository.findAllByDoctor(doctor.get());
    }

    public Optional<List<Visit>> findAllByDoctor(String surname) {
        Optional<Doctor> doctor = doctorRepository.find(surname);
        if (doctor.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(visitRepository.findAllByDoctor(doctor.get()));
        }
    }

    public Optional<Visit> findByDoctor(String surname, Long id) {
        Optional<Doctor> doctor = doctorRepository.find(surname);
        if (doctor.isEmpty()) {
            return Optional.empty();
        } else {
            return visitRepository.findByIdAndDoctor(id, doctor.get());
        }
    }

    @Transactional
    public void createForDoctor(Visit visit, String surname) {
        Doctor doctor = doctorRepository.find(surname).orElseThrow();
        visit.setDoctor(doctorRepository.find(surname).orElseThrow());
        doctor.getVisits().add(visit);
        visitRepository.create(visit);
    }

    @Transactional
    public void delete(Long visitId) {
        Visit visit = visitRepository.find(visitId).orElseThrow();
        visit.getDoctor().getVisits().remove(visit);
        visitRepository.delete(visit);
    }

    @Transactional
    public void update(Visit visit) {
        Visit original = visitRepository.find(visit.getId()).orElseThrow();
        visitRepository.detach(original);
        if (!original.getDoctor().getSurname().equals(visit.getDoctor().getSurname())) {
            original.getDoctor().getVisits().removeIf(visitToRemove -> visitToRemove.getId().equals(visit.getId()));
            doctorRepository.find(visit.getDoctor().getSurname()).ifPresent(doctor -> doctor.getVisits().add(visit));
        }
        visitRepository.update(visit);
    }
}
