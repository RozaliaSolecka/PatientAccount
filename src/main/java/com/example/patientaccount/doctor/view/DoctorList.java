package com.example.patientaccount.doctor.view;

import com.example.patientaccount.doctor.model.DoctorsModel;
import com.example.patientaccount.doctor.service.DoctorService;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class DoctorList implements Serializable {
    private final DoctorService service;
    private DoctorsModel doctors;

    @Inject
    public DoctorList(DoctorService service) {
        this.service = service;
    }

    public DoctorsModel getDoctors() {
        if (doctors == null) {
            doctors = DoctorsModel.entityToModelMapper().apply(service.findAll());
        }
        return doctors;
    }

    public String deleteAction(DoctorsModel.Doctor doctor) {
        service.delete(doctor.getSurname());
        return "doctor_list?faces-redirect=true";
    }
}
