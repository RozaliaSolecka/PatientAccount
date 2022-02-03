package com.example.patientaccount.doctor.repository;

import com.example.patientaccount.datastore.DataStore;
import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.repository.Repository;
import lombok.extern.java.Log;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.print.Doc;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
@Log
public class DoctorRepository implements Repository<Doctor, String> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Doctor> find(String surname) {
        return Optional.ofNullable(em.find(Doctor.class, surname));
    }

    @Override
    public List<Doctor> findAll() {
        return em.createQuery("select c from Doctor c", Doctor.class).getResultList();
    }

    @Override
    public void create(Doctor entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Doctor entity) {
        em.remove(em.find(Doctor.class, entity.getSurname()));
    }

    @Override
    public void update(Doctor entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Doctor entity) {
        em.detach(entity);
    }
}