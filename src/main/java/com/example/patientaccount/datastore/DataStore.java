package com.example.patientaccount.datastore;

import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.entity.Visit;
import lombok.extern.java.Log;
import com.example.patientaccount.serialization.CloningUtility;
import com.example.patientaccount.user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * For the sake of simplification instead of using real database this example is using an data source object which
 * should be put in servlet context in a single instance. In order to avoid {@link
 * java.util.ConcurrentModificationException} all methods are synchronized. Normally synchronization would be carried on
 * by the database server.
 */
@Log
@ApplicationScoped
public class DataStore {
    private Set<User> users = new HashSet<>();
    private Set<Doctor> doctors = new HashSet<>();
    private Set<Visit> visits = new HashSet<>();

    public synchronized Optional<User> findUser(Long personalIdNumber) {
        return users.stream()
                .filter(user -> user.getPersonalIdNumber().equals(personalIdNumber))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUser(User user) throws IllegalArgumentException {
        user.setPersonalIdNumber(findAllUsers().stream()
                .mapToLong(User::getPersonalIdNumber)
                .max().orElse(0) + 1);
        users.add(CloningUtility.clone(user));
    }

    public synchronized void updateUser(User user) throws IllegalArgumentException {
        findUser(user.getPersonalIdNumber()).ifPresentOrElse(
                original -> {
                    users.remove(original);
                    users.add(CloningUtility.clone(user));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The user with id \"%d\" does not exist", user.getPersonalIdNumber()));
                });
    }

    public synchronized List<Doctor> findAllDoctors() {
        return doctors.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized Optional<Doctor> findDoctor(String surname) {
        return doctors.stream()
                .filter(doctor -> doctor.getSurname().equals(surname))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createDoctor(Doctor doctor) throws IllegalArgumentException {
        findDoctor(doctor.getSurname()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The doctor surname \"%s\" is not unique", doctor.getSurname()));
                },
                () -> doctors.add(CloningUtility.clone(doctor)));
    }
    public synchronized void updateDoctor(Doctor doctor) throws IllegalArgumentException {
        findDoctor(doctor.getSurname()).ifPresentOrElse(
                original -> {
                    doctors.remove(original);
                    doctors.add(CloningUtility.clone(doctor));
                    List<Visit> visits = findAllVisitsByDoctor(doctor.getSurname());
                    visits.forEach(visit -> visit.setDoctor(doctor));
                    visits.forEach(visit -> updateVisit(visit));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The doctor with id \"%d\" does not exist", doctor.getSurname()));
                });
    }
    public synchronized void deleteDoctor(String surname) throws IllegalArgumentException {
        findDoctor(surname).ifPresentOrElse(
                original -> {
                    List<Visit> visitLIst = findAllVisitsByDoctor(surname);
                    visitLIst.forEach(vis -> deleteVisit(vis.getId()));
                    doctors.remove(original);

                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The doctor with id \"%d\" does not exist", surname));
                });
    }

    public synchronized List<Visit> findAllVisits() {
        return visits.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized Optional<Visit> findVisit(Long id) {
        return visits.stream()
                .filter(visit -> visit.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<Visit> findAllVisitsByDoctor(String surname) throws IllegalArgumentException {
        Optional<Doctor> doctorObject = findDoctor(surname);
        if(doctorObject.isPresent()){
            return visits.stream()
                    .filter(visit -> visit.getDoctor().getSurname().equals(surname))
                    .map(CloningUtility::clone)
                    .collect(Collectors.toList());
        }
        else{
            throw new IllegalArgumentException(String.format("Vists doesnt exist", surname));
        }

    }

    public synchronized void createVisit(Visit visit) throws IllegalArgumentException {
        visit.setId(findAllVisits().stream()
                .mapToLong(Visit::getId)
                .max().orElse(0) + 1);
        Optional<Doctor> doctor = findDoctor(visit.getDoctor().getSurname());
        if(doctor.isPresent()){
            visits.add(CloningUtility.clone(visit));
            doctor.get().getVisits().add(visit);
            updateDoctor(doctor.get());
        }

    }
    public synchronized void deleteVisit(Long id) throws IllegalArgumentException {
        findVisit(id).ifPresentOrElse(
                original -> visits.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The visit with id \"%d\" does not exist", id));
                });

    }

    public synchronized void updateVisit(Visit visit) throws IllegalArgumentException {
        findVisit(visit.getId()).ifPresentOrElse(
                original -> {
                    visits.remove(original);
                    visits.add(CloningUtility.clone(visit));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The visit with id \"%d\" does not exist", visit.getId()));
                });
    }
}
