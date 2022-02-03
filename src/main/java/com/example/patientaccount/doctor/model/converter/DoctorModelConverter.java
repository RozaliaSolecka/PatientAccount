package com.example.patientaccount.doctor.model.converter;

import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.model.DoctorModel;
import com.example.patientaccount.doctor.model.DoctorsModel;
import com.example.patientaccount.doctor.service.DoctorService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.util.Optional;

@FacesConverter(forClass = DoctorModel.class, managed = true)
public class DoctorModelConverter implements Converter<DoctorModel> {
    private final DoctorService doctorService;

    @Inject
    public DoctorModelConverter(DoctorService service) {
        this.doctorService = service;
    }

    @Override
    public DoctorModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        Optional<Doctor> doctor = doctorService.find(value);
        return doctor.isEmpty() ? null : DoctorModel.entityToModelMapper().apply(doctor.get());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, DoctorModel value) {
        return value == null ? "" : value.getSurname();
    }


}
