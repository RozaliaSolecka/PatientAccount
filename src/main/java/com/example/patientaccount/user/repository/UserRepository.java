package com.example.patientaccount.user.repository;

import com.example.patientaccount.datastore.DataStore;
import com.example.patientaccount.repository.Repository;
import com.example.patientaccount.serialization.CloningUtility;
import com.example.patientaccount.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Repository for character entity. Repositories should be used in business layer (e.g.: in services).
 */
@RequestScoped
public class UserRepository implements Repository<User, Long> {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> find(Long id) {

        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        em.persist(entity);;
    }

    @Override
    public void update(User entity) {
        em.merge(entity);
    }

    @Override
    public void delete(User entity) {
        em.remove(em.find(User.class, entity.getLogin()));
    }

    @Override
    public void detach(User entity) {
        em.detach(entity);
    }
}

