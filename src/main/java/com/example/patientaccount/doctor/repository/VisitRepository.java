package com.example.patientaccount.doctor.repository;

import com.example.patientaccount.datastore.DataStore;
import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.entity.Visit;
import com.example.patientaccount.repository.Repository;
import com.example.patientaccount.serialization.CloningUtility;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class VisitRepository implements Repository<Visit, Long> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Visit> find(Long id) {
        return Optional.ofNullable(em.find(Visit.class, id));
    }

    @Override
    public List<Visit> findAll() {
        return em.createQuery("select c from Visit c", Visit.class).getResultList();
    }

    @Override
    public void create(Visit entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Visit entity) {
        em.remove(em.find(Visit.class, entity.getId()));
    }

    @Override
    public void update(Visit entity) {
        em.merge(entity);
    }

    public List<Visit> findAllByDoctor(Doctor doctor) {
        return em.createQuery("select c from Visit c where c.doctor = :doctor", Visit.class)
                .setParameter("doctor", doctor)
                .getResultList();
    }

    public Optional<Visit> findByIdAndDoctor(Long id, Doctor doctor) {
        try {
            return Optional.of(em.createQuery("select c from Visit c where c.id = :id and c.doctor = :doctor", Visit.class)
                    .setParameter("doctor", doctor)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void detach(Visit entity) {
        em.detach(entity);
    }
}
