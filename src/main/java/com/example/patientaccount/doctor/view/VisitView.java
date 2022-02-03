package com.example.patientaccount.doctor.view;

import com.example.patientaccount.doctor.entity.Visit;
import com.example.patientaccount.doctor.model.VisitModel;
import com.example.patientaccount.doctor.service.VisitService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;


@RequestScoped
@Named
public class VisitView implements Serializable {
    private final VisitService visitService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private VisitModel visit;

    @Inject
    public VisitView(VisitService service) {
        this.visitService = service;
    }

    public void init() throws IOException {
        Optional<Visit> visit = visitService.find(id);
        if (visit.isPresent()) {
            this.visit = VisitModel.entityToModelMapper().apply(visit.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Visit not found");
        }
    }
}