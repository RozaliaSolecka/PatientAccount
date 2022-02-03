package com.example.patientaccount.doctor.view;

import com.example.patientaccount.doctor.entity.Doctor;
import com.example.patientaccount.doctor.entity.Visit;
import com.example.patientaccount.doctor.model.DoctorModel;
import com.example.patientaccount.doctor.model.VisitsModel;
import com.example.patientaccount.doctor.service.DoctorService;
import com.example.patientaccount.doctor.service.VisitService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ViewScoped
@Named
public class DoctorView implements Serializable {
    private final DoctorService doctorService;
    private final VisitService visitService;

    @Setter
    @Getter
    private String surname;

    @Getter
    private DoctorModel doctor;

    @Getter
    private VisitsModel visitsModel;

    @Inject
    public DoctorView(DoctorService doctorService, VisitService visitService) {

        this.doctorService = doctorService;
        this.visitService = visitService;
    }

    public void init() throws IOException {
        Optional<Doctor> doctor = doctorService.find(surname);
        if (doctor.isPresent()) {
            this.doctor = DoctorModel.entityToModelMapper().apply(doctor.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Character not found");
        }
    }

    public VisitsModel getVisits() {
        if (visitsModel == null) {
            visitsModel = VisitsModel.entityToModelMapper().apply(visitService.findAllForDoctor(doctor.getSurname()));
        }
        return visitsModel;
    }

    public String deleteAction(Long visitId) {
        doctor.getVisits().remove(visitService.find(visitId).orElseThrow());
        visitService.delete(visitId);
        Doctor doctorS = doctorService.find(surname).get();
        DoctorModel.modelToEntityUpdater().apply(doctorS, doctor);
        doctorService.update(doctorService.find(surname).get());
        return "doctor_list?faces-redirect=true";
    }
}
